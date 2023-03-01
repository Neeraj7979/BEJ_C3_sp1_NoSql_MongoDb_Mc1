package com.niit.bej.c2_sp3_NoSql_Mc1.controller;

import com.niit.bej.c2_sp3_NoSql_Mc1.domain.Track;
import com.niit.bej.c2_sp3_NoSql_Mc1.exception.TrackAlreadyExistException;
import com.niit.bej.c2_sp3_NoSql_Mc1.exception.TrackNotFoundException;
import com.niit.bej.c2_sp3_NoSql_Mc1.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TrackController {
    private final TrackService trackService;

    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @PostMapping("/saveTrack")
    public ResponseEntity<?> saveTrack(@RequestBody Track track) {
        try {
            Track saveNewTrack = trackService.saveTrack(track);
            return new ResponseEntity<>(trackService, HttpStatus.ACCEPTED);
        } catch (TrackAlreadyExistException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/deleteTrack/{id}")
    public ResponseEntity<?> deleteTrackById(@PathVariable Integer id) {
        try {
            boolean status = trackService.deleteTrackById(id);
            if (status == true)
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            else
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (TrackNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllTrack")
    public ResponseEntity<?> getAllTrack() {
        try {
            List<Track> allTracks = trackService.getAllTrack();
            return new ResponseEntity<>(allTracks, HttpStatus.FOUND);
        } catch (TrackNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/getAllTrack/{rating}")
    public ResponseEntity<?> getTrackByRatingGreaterThanFour(double rating) {
        try {
            List<Track> trackRatingGreaterThanFour = trackService.getTrackByRatingGreaterThanGivenRating(rating);
            return new ResponseEntity<>(trackRatingGreaterThanFour, HttpStatus.FOUND);
        } catch (TrackNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}

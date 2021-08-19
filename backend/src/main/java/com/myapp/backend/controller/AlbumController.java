package com.myapp.backend.controller;

import com.myapp.backend.domain.dto.album.AlbumDetailDto;
import com.myapp.backend.domain.dto.album.AlbumDto;
import com.myapp.backend.domain.dto.album.AlbumResultDto;
import com.myapp.backend.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @PostMapping
    public ResponseEntity albumInsert(@RequestBody AlbumDto albumDto) {
        int result = 0;

        result = albumService.albumInsert(albumDto);

        if(result == 1){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{albumId}")
    public ResponseEntity getAlbumDetail(@PathVariable("albumId") int albumId){
        AlbumDetailDto albumDetailDto = albumService.getAlbumDetail(albumId);

        if(albumDetailDto == null || albumDetailDto.getDownloadUrl() == null || albumDetailDto.getAlbumImgs() == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(albumDetailDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity albumDelete(@RequestParam(required = true)final int albumId){
        int result = 0;

        result = albumService.albumDelete(albumId);

        if(result == 1){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(111,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<AlbumResultDto> albumThumbnail(@RequestParam String classCode, @RequestParam int pageNum){
        AlbumResultDto result = albumService.albumThumbnail(classCode, pageNum);

        return new ResponseEntity(result, HttpStatus.OK);
    }
}

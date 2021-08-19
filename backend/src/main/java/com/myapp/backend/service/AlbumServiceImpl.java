package com.myapp.backend.service;

import com.myapp.backend.domain.dto.album.AlbumDto;
import com.myapp.backend.domain.dto.album.AlbumResultDto;
import com.myapp.backend.domain.dto.album.AlbumThumbnailDto;
import com.myapp.backend.domain.dto.album.AlbumDetailDto;
import com.myapp.backend.domain.entity.Album;
import com.myapp.backend.domain.entity.AlbumImg;
import com.myapp.backend.repository.AlbumImgRepository;
import com.myapp.backend.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService{

    private static final int SUCCESS = 1;
    private static final int FAIL = -1;


    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    AlbumImgRepository albumImgRepository;



    @Override
    public int albumInsert(AlbumDto albumDto) {
        try {
            // 일단 글 부분과 list의 첫번째 사진을 넣고 id값을 받아온다.
            // useGeneratedKeys="true" keyProperty="albumId"
            Album album = new Album();
            album.setTitle(albumDto.getTitle());
            album.setDownloadUrl(albumDto.getDownloadUrl());
            album.setClassCode(albumDto.getClassCode());
            album.setWriterId(albumDto.getWriterId());
            album.setThumbnailUrl(albumDto.getPhotoList().get(0));


            albumRepository.save(album);
            int albumId = album.getAlbumId();

            // list로 받아온 모든 file url을 album_img에 넣는다.
            ArrayList<String> list = albumDto.getPhotoList();

            for (String photoId : list) {
                AlbumImg albumImg = new AlbumImg();
                albumImg.setAlbumId(albumId);
                albumImg.setPhotoId(photoId);

                albumImgRepository.save(albumImg);
            }
            return SUCCESS;

        } catch (Exception e) {
            return FAIL;
        }
    }

    @Override
    @Transactional
    public AlbumDetailDto getAlbumDetail(int albumId) {
        String downloadUrl = albumRepository.findByAlbumId(albumId).getDownloadUrl();
        List<AlbumImg> albumImgs = albumImgRepository.findByAlbumId(albumId);
        return new AlbumDetailDto(downloadUrl, albumImgs);
    }

    @Transactional
    @Override
    public int albumDelete(int albumId) {
        try{
            albumImgRepository.deleteAllByAlbumId(albumId);

            albumRepository.deleteById(albumId);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }

    @Override
    public AlbumResultDto albumThumbnail(String classCode, int pageNum){
        AlbumResultDto result=new AlbumResultDto();
        int pageCnt=15;
        Pageable page = PageRequest.of(pageNum, pageCnt);
        Page<Album> pages = albumRepository.findByClassCodeOrderByAlbumIdDesc(classCode, page);
        result.setPageCnt(pages.getTotalPages());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


        List<AlbumThumbnailDto> albumList=new ArrayList<>();
        for (Album a : pages){
            albumList.add(new AlbumThumbnailDto(a.getAlbumId(),
                    a.getTitle(),
                    a.getThumbnailUrl(),
                    a.getCreateDate(),
                    simpleDateFormat.format(a.getCreateDate())
            ));
        }
        result.setThumbnailList(albumList);

        return result;
    }
}

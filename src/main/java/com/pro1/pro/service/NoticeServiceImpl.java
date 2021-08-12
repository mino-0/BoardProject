package com.pro1.pro.service;

import com.pro1.pro.domain.Notice;
import com.pro1.pro.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;

    @Override
    public void register(Notice notice) throws Exception {
        noticeRepository.save(notice);
    }

    @Override
    public List<Notice> list() throws Exception {
        return noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "noticeNo"));
    }

    @Override
    public Notice read(Long noticeNo) throws Exception {
        return noticeRepository.getOne(noticeNo);
    }

    @Override
    public void modify(Notice notice) throws Exception {
        Notice noticeEntity = noticeRepository.getOne(notice.getNoticeNo());

        noticeEntity.setTitle(notice.getTitle());

        noticeRepository.save(noticeEntity);
    }

    @Override
    public void remove(Long noticeNo) throws Exception {
        noticeRepository.deleteById(noticeNo);

    }
}

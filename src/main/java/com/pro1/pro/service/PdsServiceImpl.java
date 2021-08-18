package com.pro1.pro.service;

import com.pro1.pro.domain.Pds;
import com.pro1.pro.domain.PdsFile;
import com.pro1.pro.repository.PdsFileRepository;
import com.pro1.pro.repository.PdsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PdsServiceImpl implements  PdsService {
    private final PdsRepository pdsRepository;
    private final PdsFileRepository pdsFileRepository;

    @Override
    public void register(Pds pds) throws Exception {
        Pds pdsEntity = new Pds();

        pdsEntity.setItemName(pds.getItemName());
        pdsEntity.setDescription(pds.getDescription());

        String[] files = pds.getFiles();

        if (files == null) {
            return;
        }

        for (String fileName : files) {
            PdsFile pdsFile = new PdsFile();

            pdsFile.setFullName(fileName);
            pdsEntity.addItemFile(pdsFile);

        }
        pdsRepository.save(pdsEntity);
    }

    @Override
    public List<Pds> list() throws Exception {
        return pdsRepository.findAll(Sort.by(Sort.Direction.DESC, "itemId"));
    }

    @Override
    public Pds read(Long itemId) throws Exception {
        Pds pdsEntity = pdsRepository.getOne(itemId);
        Integer viewCnt = pdsEntity.getViewCnt();

        if (viewCnt == null) {
            viewCnt = 0;
        }
        pdsEntity.setViewCnt(viewCnt + 1);
        pdsRepository.save(pdsEntity);
        return pdsRepository.getOne(itemId);
    }

    @Override
    public void modify(Pds pds) throws Exception {
        Pds pdsEntity = pdsRepository.getOne(pds.getItemId());

        pdsEntity.setItemName(pds.getItemName());
        pdsEntity.setDescription(pds.getDescription());

        String[] files = pds.getFiles();

        if (files != null) {
            pdsEntity.clearItemFile();

            for (String fileName : files) {
                PdsFile pdsFile = new PdsFile();
                pdsFile.setFullName(fileName);

                pdsEntity.addItemFile(pdsFile);
            }
        }
        pdsRepository.save(pdsEntity);
    }

    @Override
    public void remove(Long itemId) throws Exception {
        pdsRepository.deleteById(itemId);
    }

    @Override
    public List<String> getAttach(Long itemId) throws Exception {
        Pds pdsEntity = pdsRepository.getOne(itemId);

        List<PdsFile> pdsFiles = pdsEntity.getPdsFiles();

        List<String> attachList = new ArrayList<String>();

        for (PdsFile pdsFile : pdsFiles) {
            attachList.add(pdsFile.getFullName());
        }
        return attachList;
    }

    @Override
    public void updateAttachDownCnt(String fullName) throws Exception {
        List<PdsFile> pdsFileList = pdsFileRepository.findByFullName(fullName);

        if (pdsFileList.size() > 0) {
            PdsFile pdsFileEntity = pdsFileList.get(0);

            Integer downCnt = pdsFileEntity.getDownCnt();
            if (downCnt == null) {
                downCnt = 0;
            }
            pdsFileEntity.setDownCnt(downCnt+1);
            pdsFileRepository.save(pdsFileEntity);
        }
    }
}


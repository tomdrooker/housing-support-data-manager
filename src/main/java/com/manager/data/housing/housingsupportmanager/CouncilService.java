package com.manager.data.housing.housingsupportmanager;

import com.manager.data.housing.housingsupportmanager.model.Council;
import com.manager.data.housing.housingsupportmanager.repository.CouncilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouncilService {

    @Autowired
    private CouncilRepository repo;

    public List<Council> listAll() {
        return repo.findAll();
    }

    public void save(Council council) {
        repo.save(council);
    }

    public Council get(Long id) {
        return repo.findById(id).get();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

}
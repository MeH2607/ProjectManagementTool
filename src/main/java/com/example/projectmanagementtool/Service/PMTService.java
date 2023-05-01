package com.example.projectmanagementtool.Service;

import com.example.projectmanagementtool.Model.Task;
import com.example.projectmanagementtool.Repository.PMTRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PMTService {
    private PMTRepository pmtRepository;
    public PMTService(ApplicationContext context, @Value("${pmt.repository}") String impl) {
        pmtRepository = (PMTRepository) context.getBean(impl);
    }

    public List<Task> getTasksFromSubproject(int subprojectID) {
        return pmtRepository.getTasksFromSubproject(subprojectID);
    }

}

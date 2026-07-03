package org.example.feignapi.controller;

import jakarta.annotation.PostConstruct;
import org.example.feignapi.dto.DataRequest;
import org.example.feignapi.dto.DataResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/data")
public class DataController {
    private Map<Long, DataResponse> dataStore = new HashMap<>();
    private Long idCounter=1L;

    @PostConstruct
    public void initDataStore(){
        dataStore.put(idCounter++, new DataResponse(1L, "Item 1", 100));
        dataStore.put(idCounter++, new DataResponse(2L, "Item 2", 200));
        dataStore.put(idCounter++, new DataResponse(3L, "Item 3", 300));
        dataStore.put(idCounter++, new DataResponse(4L, "Item 4", 400));
        dataStore.put(idCounter++, new DataResponse(5L, "Item 5", 500));
    }
    //read
    @GetMapping("/{id}")
    public DataResponse getDataById(@PathVariable Long id){
        DataResponse dataResponse = dataStore.get(id);
        if(dataResponse==null){
            throw new RuntimeException("Data not found"+id);
        }
        return dataResponse;
    }
    //create
    @PostMapping
    public DataResponse createData(@RequestBody DataRequest request){
        DataResponse build = DataResponse.builder()
                .id(idCounter++)
                .value(request.getValue())
                .name(request.getName())
                .build();

        dataStore.put(build.getId(),build);
        return build;
    }
    //update
    @PutMapping("/{id}")
    public DataResponse updateData(
            @PathVariable("id") Long id,
            @RequestBody DataRequest request
    ){
        DataResponse response = dataStore.get(id);

        if(response ==null){
            throw new RuntimeException("Data not found(update) "+id);
        }
        response.setName(request.getName());
        response.setValue(request.getValue());
        dataStore.put(id,response);

        return response;
    }
    //delete
    @DeleteMapping("/{id}")
    public String deleteData(@PathVariable Long id){
        DataResponse removed = dataStore.remove(id);
        if(removed ==null){
            throw new RuntimeException("Data not found(delete) "+id);
        }
        return "Data deleted with id: "+id;
    }
}

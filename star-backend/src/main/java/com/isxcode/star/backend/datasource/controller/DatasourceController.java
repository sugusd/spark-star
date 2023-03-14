package com.isxcode.star.backend.datasource.controller;

import com.isxcode.star.backend.ReqDto;
import com.isxcode.star.backend.datasource.pojo.DatasourceEntity;
import com.isxcode.star.backend.datasource.service.DatasourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/datasource")
public class DatasourceController {

    private final DatasourceService datasourceService;

    @PostMapping("/addDatasource")
    public void addDatasource(@RequestBody ReqDto reqDto) {

        datasourceService.addDatasource(reqDto);
    }

    @GetMapping("/queryDatasource")
    public List<DatasourceEntity> queryDatasource() {

        return datasourceService.queryDatasource();
    }

    @PostMapping("/delDatasource")
    public void delDatasource(@RequestBody ReqDto reqDto) {

        datasourceService.delDatasource(reqDto);
    }

    @PostMapping("/delDatasources")
    public void delDatasources(@RequestBody ReqDto reqDto) {

        datasourceService.delDatasources(reqDto);
    }

    @PostMapping("/updateDatasource")
    public void updateDatasource(@RequestBody ReqDto reqDto) {

        datasourceService.updateDatasource(reqDto);
    }

    @PostMapping("/getDatasource")
    public DatasourceEntity getDatasource(@RequestBody ReqDto reqDto) {

        return datasourceService.getDatasource(reqDto);
    }

    @PostMapping("/checkDatasource")
    public boolean checkDatasource(@RequestBody ReqDto reqDto) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        return datasourceService.checkDatasource(reqDto);
    }

}

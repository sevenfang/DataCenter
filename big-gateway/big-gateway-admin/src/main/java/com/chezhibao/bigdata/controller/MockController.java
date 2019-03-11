package com.chezhibao.bigdata.controller;

import com.chezhibao.bigdata.service.MockService;
import com.chezhibao.bigdata.vo.MockVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/11.
 */
@RestController
@RequestMapping("/gateway/admin")
public class MockController {

    @Autowired
    private MockService mockService;

    @PostMapping("mock")
    public Object mock(@RequestBody MockVO mockVO) {
        String method = mockVO.getMethod();
        return mockService.send(mockVO.getURL(),method, mockVO.getParams());
    }

}

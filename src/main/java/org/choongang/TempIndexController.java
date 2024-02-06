package org.choongang;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 임시 컨트롤러 - 인덱스 페이지로 임시 매핑
 */
@Controller
@RequiredArgsConstructor
public class TempIndexController {

    private final Utils utils;

    @GetMapping("/")
    public String index() {
        return utils.tpl("layouts/main");
    }
}

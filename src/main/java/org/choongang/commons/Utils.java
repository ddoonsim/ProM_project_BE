package org.choongang.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.controllers.BasicConfig;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component    // 스프링 관리 객체로 등록
@RequiredArgsConstructor
public class Utils {

    private final HttpServletRequest request ;
    private final HttpSession session ;
    private final FileInfoService fileInfoService;

    private static final ResourceBundle commonsBundle;
    private static final ResourceBundle validationsBundle;
    private static final ResourceBundle errorsBundle;

    static {
        commonsBundle = ResourceBundle.getBundle("messages.commons");
        validationsBundle = ResourceBundle.getBundle("messages.validations");
        errorsBundle = ResourceBundle.getBundle("messages.errors");
    }

    /**
     * device 모드 수동 전환 혹은
     * 요청 헤더의 User-Agent 정보를 가지고 모바일인지 체크
     */
    public boolean isMobile() {
        // 모바일 수동 전환 모드 체크
        String device = (String) session.getAttribute("device") ;
        if (StringUtils.hasText(device)) {
            return device.equals("MOBILE") ;
        }

        String ua = request.getHeader("User-Agent") ;  // User-Agent 가져오기

        // 모바일 장비 체크를 위한 정규 표현식
        String pattern = ".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*" ;

        return ua.matches(pattern) ;    // 모바일이면 true
    }

    /**
     * device가 모바일인지 PC인지에 따라 view 경로 분리
     */
    public String tpl(String path) {
        String prefix = isMobile() ? "mobile/" : "front/" ;

        return prefix + path ;
    }


    public static String getMessage(String code, String type) {
        try {
            type = StringUtils.hasText(type) ? type : "validations";

            ResourceBundle bundle = null;
            if (type.equals("commons")) {
                bundle = commonsBundle;
            } else if (type.equals("errors")) {
                bundle = errorsBundle;
            } else {
                bundle = validationsBundle;
            }

            return bundle.getString(code);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // 타입을 지정하지 않은 경우 ==> validations가 default로 설정되어 있음
    public static String getMessage(String code) {
        return getMessage(code, null);
    }

    /* https://github.com/yonggyo1125/BOARD_BE/commit/c9a8ebd61021e6914dfe6b4fc61749814a3ac982 */
    public static Map<String, List<String>> getMessages(Errors errors) {
        try {
            Map<String, List<String>> data = new HashMap<>();
            for (FieldError error : errors.getFieldErrors()) {
                String field = error.getField();
                List<String> messages = Arrays.stream(error.getCodes()).sorted(Comparator.reverseOrder())
                        .map(c -> getMessage(c))
                        .filter(s -> s != null && !s.isBlank())
                        .toList();

                data.put(field, messages);
            }
            return data;

        } catch (Exception e) {
            return null;
        }

    }
//        return errors.getFieldErrors()
//                .stream()
//                .flatMap(f -> Arrays.stream(f.getCodes()).sorted(Comparator.reverseOrder())
//                        .map(c -> getMessage(c)))
//                .filter(s -> s != null && !s.isBlank()).toList();

    /**
     * \n 또는 \r\n -> <br>
     * @param str
     * @return
     */
    public String nl2br(String str) {
        str = Objects.requireNonNullElse(str, "");

        str = str.replaceAll("\\n", "<br>")
                .replaceAll("\\r", "");

        return str;
    }

    /**
     * 썸네일 이미지 사이즈 설정
     * @return
     */
    public List<int[]> getThumbSize() {
        BasicConfig config = (BasicConfig) request.getAttribute("siteConfig");
        String thumbSize = config.getThumbSize(); // \r\n
        String[] thumbsSize = thumbSize.split("\\n");

        List<int[]> data = Arrays.stream(thumbsSize)
                .filter(StringUtils::hasText)
                .map(s -> s.replaceAll("\\s+","")) // 공백제거
                .map(this::toConvert).toList();

        return data;
    }

    private int[] toConvert(String size) {
        size = size.trim();
        int[] data = Arrays.stream(size.replaceAll("\\r", "").toUpperCase().split("X")).mapToInt(Integer::parseInt).toArray();
        // 쪼개서 대소문자 구분없이 만들고 정수로 만들어서 배열했다.

        return data;
    }

    public String printThumb(long seq, int width, int height, String className) {
        try {
            String[] data = fileInfoService.getThumb(seq, width, height);
            if (data != null) {
                String cls = StringUtils.hasText(className) ? " class ='" + className + "'" : "";
                String image = String.format("<img src='%s'%s>", data[1], cls);
                return image;
            }
        } catch (Exception e) {}

        return "";
    }

    public String printThumb(long seq, int width, int height) {
        return  printThumb(seq, width, height, null);
    }
    /**
     * 0이하 정수 인 경우 1이상 정수로 대체
     *
     * @param num
     * @param replace
     * @return
     */
    public static int onlyPositiveNumber(int num, int replace) {
        return num < 1 ? replace : num;
    }



    /**
     * 알파벳, 숫자, 특수문자 조합 랜덤 문자열 생성
     */
    public String randomChars() {
        return randomChars(8);
    }

    public String randomChars(int length) {
        // 알파벳 생성
        Stream<String> alphas = IntStream.concat(IntStream.rangeClosed((int)'a', (int)'z'), IntStream.rangeClosed((int)'A', (int)'Z')).mapToObj(s -> String.valueOf((char)s));

        // 숫자 생성
        Stream<String> nums = IntStream.range(0, 10).mapToObj(String::valueOf);

        // 특수문자 생성
        Stream<String> specials = Stream.of("~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+", "-", "=", "[", "{", "}", "]", ";", ":");

        List<String> chars = Stream.concat(Stream.concat(alphas, nums), specials).collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(chars);

        return chars.stream().limit(length).collect(Collectors.joining());
    }


    public String backgroundStyle(FileInfo file) {

/*        String imageUrl = file.getFileUrl();
        List<String> thumbsUrl = file.getThumbsUrl();
        if (thumbsUrl != null && !thumbsUrl.isEmpty()) {
            imageUrl = thumbsUrl.get(thumbsUrl.size() - 1);
            String style = String.format("background:url('%s') no-repeat center center; background-size:cover;", imageUrl);
        }

        */

        return backgroundStyle(file, 100, 100);
    }

    public String backgroundStyle(FileInfo file, int width, int height) {
        try {
            String[] data = fileInfoService.getThumb(file.getSeq(), width, height);
            String imageUrl = data[1];

            String style = String.format("background:url('%s') no-repeat center center; background-size:cover; width: %dpx; height:%dpx", imageUrl, width, height);

            return style;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}

package org.choongang.commons;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MessageUtil {
    private final MessageSource messageSource;

    public Map<String, List<String>> getMessages(Errors errors) {
        Map<String, List<String>> messages = errors.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, f -> _getMessages(f.getCodes())));

        List<String> messages2 = errors.getGlobalErrors().stream()
                .flatMap(o -> _getMessages(o.getCodes()).stream()).toList();
        if(messages2 != null && !messages2.isEmpty()) {
            messages.put("global", messages2);
        }

        return messages;
    }

    public List<String> _getMessages(String[] codes) {
        List<String> messages = Arrays.stream(codes).map(c -> {
            try {
                return messageSource.getMessage(c, null, null);
            } catch (Exception e) {
                return "";
            }
        }).filter(s -> !s.isBlank()).toList();

        return messages;
    }
}

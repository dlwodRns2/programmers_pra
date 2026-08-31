package org.example.webservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BoardListItemResponseDto {

    private Long id;
    private String title;
    private String userId;
    private String userName;
    private Long commentCount;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    private LocalDateTime created;
}

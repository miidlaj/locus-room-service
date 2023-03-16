package com.midlaj.room.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resort {

    private Long resortId;

    private String resortName;

    private String resortAddress;

    private String resortCode;

}

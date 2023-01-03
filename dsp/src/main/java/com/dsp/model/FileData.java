package com.dsp.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "FileData")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileData 
{
    @Id
    private int adid;
    private String name;
    private String type;
    @Lob
    @Column(name = "filedata",length = 1000)
    private byte[] fileData;
}

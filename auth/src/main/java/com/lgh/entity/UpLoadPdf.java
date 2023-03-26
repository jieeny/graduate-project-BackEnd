package com.lgh.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 李广辉
 * @date 2023/2/9 21:42
 */

@Data
public class UpLoadPdf {

//    private String PdfId;
//    private MultipartFile file;
    private String pdfBase64;
    private String orderId;

}

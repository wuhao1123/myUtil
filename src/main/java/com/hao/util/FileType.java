package com.hao.util;

/**
 * 类FileType.java的实现描述:文件类型枚取
 * 
 * @author 吴昊 2018年3月6日20:02:38
 */
public enum FileType {

                      /**
                       * JEPG.jpg
                       */
                      JPG("FFD8FF", "jpg"),

                      /**
                       * PNG.
                       */
                      PNG("89504E47", "png"),

                      /**
                       * GIF.
                       */
                      GIF("47494638", "gif"),
                      /**
                       * tif标签图像文件格式
                       */
                      TIF("49492A00", "tif"),
                      /**
                       * Windows Bitmap.
                       */
                      BMP("424D", "bmp"),

                      /**
                       * MS Word/Excel.
                       */
                      XLS_DOC("D0CF11E0", "excel"),

                      /**
                       * exe
                       */
                      EXE("4d5a", "exe"),

                      /**
                       * Adobe Acrobat.
                       */
                      PDF("255044462D312E", "pdf"),

                      /**
                       * tiff.
                       */
                      TIFF("49492a00", "tiff"),

                      /**
                       * cad.
                       */
                      DWG("41433130", "dwg"),

                      /**
                       * adobe photoshop.
                       */
                      PSD("38425053", "psd"),

                      /**
                       * rich text format.
                       */
                      PTF("7b5c727466", "ptf"),

                      /**
                       * xml.
                       */
                      XML("3c3f786d6c", "xml"),

                      /**
                       * html.
                       */
                      HTML("68746d6c3e", "html"),

                      /**
                       * email [thorough only].
                       */
                      EML("44656c69766572792d646174653a", "eml"),

                      /**
                       * outlook express.
                       */
                      DBX("cfad12fec5fd746f", "dbx"),

                      /**
                       * outlook (pst).
                       */
                      PST("2142444e", "pst"),

                      /**
                       * ms access.
                       */
                      MDB("5374616e64617264204a", "mdb"),

                      /**
                       * wordperfect.
                       */
                      WPD("ff575043", "wpd"),

                      /**
                       * postscript.
                       */
                      EPS("252150532d41646f6265", "eps"),

                      /**
                       * quicken.
                       */
                      QDF("ac9ebd8f", "qdf"),

                      /**
                       * windows password.
                       */
                      PWL("e3828596", "pwl"),

                      /**
                       * zip archive.
                       */
                      ZIP("504b0304", "zip"),

                      /**
                       * rar archive.
                       */
                      RAR("52617221", "rar"),

                      /**
                       * wave.
                       */
                      WAV("57415645", "wav"),

                      /**
                       * avi.
                       */
                      AVI("41564920", "avi"),

                      /**
                       * real audio.
                       */
                      RAM("2e7261fd", "ram"),

                      /**
                       * real media.
                       */
                      RM("2e524d46", "rm"),

                      /**
                       * mpeg (mpg).
                       */
                      MPG("000001ba", "mpg"),

                      /**
                       * quicktime.
                       */
                      MOV("6d6f6f76", "mov"),

                      /**
                       * windows media.
                       */
                      ASF("3026b2758e66cf11", "asf"),

                      /**
                       * midi.
                       */
                      MID("4d546864", "mid");

    private String value;
    private String code;

    /**
     * @param value
     * @param code
     */
    private FileType(String value, String code){
        this.value = value;
        this.code = code;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

}

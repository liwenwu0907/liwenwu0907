package com.example.demo.util.encryptionAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 内部使用的公钥私钥的配置
 * @author  liwenwu
 * @date  2021/12/8
 **/
@Configuration
@Import(value = {})
public class EncryptionConfig {

    //没有配置时的默认公钥
    private static final String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg3CXSqLU/jo4AFyjb3J5jzjS7OerCWT2\n" +
            "DpYJf4woDifyGrJnPomVa2R8ggPrG6bb0fDe/++7plqyB4hdWLjcpINJQLPJYHEHrkm06SZ6MknA\n" +
            "9/f5h9ks7VAlemUAOyz/n7WUR3mjm5VrXE82Hv51ez6nsGrpE4ZaSU9oMD9KrKTbaVq8a+dX0ZDG\n" +
            "qDxra9saGepXXshFMgO363OOFh+qfK+z5hkIqAfQVP/qKIxA6HT1/bJTqn3EHOYEnibb7XcJXdim\n" +
            "ipQ9bh2d09sIXqL97GHwyU9wKYsodqDGIS4vKt2oAfGkpLEiwXaAywOtIYJu+4K2UofvYyhjxxR7\n" +
            "R/vSgQIDAQAB";
    //没有配置时的默认私钥
    private static final String priKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCDcJdKotT+OjgAXKNvcnmPONLs\n" +
            "56sJZPYOlgl/jCgOJ/Iasmc+iZVrZHyCA+sbptvR8N7/77umWrIHiF1YuNykg0lAs8lgcQeuSbTp\n" +
            "JnoyScD39/mH2SztUCV6ZQA7LP+ftZRHeaOblWtcTzYe/nV7PqewaukThlpJT2gwP0qspNtpWrxr\n" +
            "51fRkMaoPGtr2xoZ6ldeyEUyA7frc44WH6p8r7PmGQioB9BU/+oojEDodPX9slOqfcQc5gSeJtvt\n" +
            "dwld2KaKlD1uHZ3T2wheov3sYfDJT3Apiyh2oMYhLi8q3agB8aSksSLBdoDLA60hgm77grZSh+9j\n" +
            "KGPHFHtH+9KBAgMBAAECggEAH9pHL7UqPLnLHHtWuAlb4BN7OiIiVtJ6tqoxfxFuG5Aeoc2eK4+Y\n" +
            "AECH09IzRa44EzZ2NDvak8Pi5ORtCrUx80RFmkJ3iMGbhOcjymSitFEfTHhxpjRBKPmFO5YCalTL\n" +
            "Vd7hIjkeJvQc4m/nw7cSrV1PsfcWqL6kXqO5sgeEhzXIP9tKVQemAvba59Gkp72qOWTuu2CRstMT\n" +
            "O7WjvBb3QPKWaX0lYS6Cwhm650i+xJm+nl/ITo/r2a4/OgDWCNC0aM0UqtmGJ92WcpeHkTrsSJwV\n" +
            "wcg3QqyQiGbypsx0WH6p1opWvnQdL1EaAmsByxHH7A3YGIYXKIoEo7IrjgPpSQKBgQDvzsFBgHQi\n" +
            "kvcF/FJoce6/9BFeKpwwYNslEv63gmow9JcF4a6B2mkimlSjk0EjGEFZrOi7pN17CaWuVw9CoZ4s\n" +
            "DrsTEtgdCR9HtQXHAQSG7/gFXcHND+q9VwaRgxME7b3zgM3iFbHXmPVTk+7CTAwBR5FTdWXiL60b\n" +
            "ZHsxRrN3owKBgQCMUJ8N4vCIFe9QT+YSK5BxX8rZ7Z1xtCoOPS9wc7/+rMkYVgyan8eOnCHMOXxo\n" +
            "OoNxoYEcxYBlSiaQfrvI+0XnspPEqeTLXHRi0bh/JznbzQzwpRDcNgfqF6XkDz4oIMVF2FcHkRxx\n" +
            "PXycFqqYA4mHj2va4QfhL7kYhEYxNhB/iwKBgQDpLlRAyxUiYWGOEnJXWSHcyQjyVeCWMjglbrj7\n" +
            "2/ZCMDOB5pb3cVd17DeM7y0RYQ56kmWjwIYUk6blRYZzNhBFqvVBcLtwUGR4J4+8mEK8qy6ymR5P\n" +
            "tcB959ddOuUNj9d6pa6Mvqe1iuhSIrCxgZzSybKIQsmVDOL4ldYsbiEonQKBgEdMuGYACaW9L6wp\n" +
            "zYkyJeXWfWeb/MNIf45YvQBxcQBioJUDNMhlpKvs3qe+Fl+ITD6ziL4sBgByW4JVQ+ru9Ci2b6wh\n" +
            "gKdtxZ5pX00Ni7MLUSX/Wr/rnL3xYlSvAjC15kiFlEO+EyvfyLAwPGOeeIYv9Oh2LODatT2mCVz8\n" +
            "UgndAoGAVUo7OYAjEB3+ggCj2jXtSQC0pjvc6kvULYgiUwfENN2mTpcZf9Rs5ZHGVQK4hxSDVVlG\n" +
            "34qu/1972IoVhnPYZfo+gLwj9+1CCSDtNYCZ8H/p6AhyxPAwmoWFlzV7TqE2TeO1h+5NJK4LXW1s\n" +
            "SdHYqEEFLxHMrKm0XcV9ROklHZk=";

    @Value("${jfh.internal.pubKey:pubKey}" + pubKey)
    private static String internalPubKey;

    public static void main(String[] args) {
        System.out.println(internalPubKey);
    }

}

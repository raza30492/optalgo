package com.jazasoft.rollgroup;

import com.jazasoft.rollgroup.model.Roll;
import com.jazasoft.util.ExcelUtil;

import java.io.File;
import java.util.Collection;

/**
 * Created by mdzahidraza on 02/10/17.
 */
public class Main {

    public static void main(String[] args) {

        String dataFile = "test-data1.xlsx";

        Collection<Roll> data = readFile(dataFile);
        data.forEach(d -> System.out.println(d));

    }

    private static Collection<Roll> readFile(String filename) {
        filename = System.getenv("RP_HOME") + File.separator + "rollgroup" + File.separator + filename;
        return ExcelUtil.readExcelXlsx(new File(filename),0,Roll.class);
    }
}

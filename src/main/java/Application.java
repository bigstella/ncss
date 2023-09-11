
import dto.CellIndex;
import dto.FileChangeInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class Application {

    private static CellIndex nameCellIndex = new CellIndex(1,1);
    private static int fileListStartRow = 5;

    public static void main(String[] args) {
        System.out.println("test");

        String excelFilePath = "C:\\SpringBoot\\ncss\\src\\main\\java\\excelFile\\temp.xlsx";

        try {
            FileInputStream fis = new FileInputStream(excelFilePath);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            // 프로젝트명 Cell Write
            setNameCell(sheet, "Rapid Collector Back");

            // 파일 Ncss 정보 Cell Write
            setNcssFileInfo(workbook, sheet);

            FileOutputStream fos = new FileOutputStream("C:\\SpringBoot\\ncss\\src\\main\\java\\excelFile\\temp3.xlsx");
            workbook.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void setNameCell(Sheet sheet, String name) {
        CellStyle nameCellStyle = getCellStyleFromCell(sheet, nameCellIndex.row, nameCellIndex.column);
        Cell nameCell = sheet.getRow(nameCellIndex.row).createCell(nameCellIndex.column);
        nameCell.setCellStyle(nameCellStyle);
        nameCell.setCellValue(name);
    }

    public static void setNcssFileInfo(Workbook workbook, Sheet sheet) {
        ArrayList<FileChangeInfo> fileChangeInfos = getFileList();
        int row = fileListStartRow;

        CellStyle borderStyle = getBorderCellStyle(workbook);

        for(FileChangeInfo el : fileChangeInfos) {
            Row writeRow = sheet.createRow(row);

            Cell filePathCell = writeRow.createCell(1);
            Cell codeAllLineCell = writeRow.createCell(2);
            Cell codeChangeLineCell = writeRow.createCell(3);
            Cell codeNonChangeLineCell = writeRow.createCell(4);

            filePathCell.setCellStyle(borderStyle);
            codeAllLineCell.setCellStyle(borderStyle);
            codeChangeLineCell.setCellStyle(borderStyle);
            codeNonChangeLineCell.setCellStyle(borderStyle);

            filePathCell.setCellValue(el.getFilePath());
            codeAllLineCell.setCellValue(el.getCodeLine());
            codeChangeLineCell.setCellValue(getRandomInt());
            codeNonChangeLineCell.setCellValue(getRandomInt());
            row +=1;
        }
    }

    public static CellStyle getCellStyleFromCell(Sheet sheet, int row, int column) {
        Cell nameCell = sheet.getRow(row).getCell(column);
        return nameCell.getCellStyle();
    }

    public static CellStyle getBorderCellStyle(Workbook workbook) {
        // 테두리 스타일 생성
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    public static int getRandomInt() {
        Random random = new Random();
        return random.nextInt(100);
    }

    public static ArrayList<FileChangeInfo> getFileList() {
        ArrayList<FileChangeInfo> fileChangeInfos = new ArrayList<>();
        for(int i=0; i<45; i++) {
            FileChangeInfo fileChangeInfo = new FileChangeInfo();
            fileChangeInfo.setFilePath("File_" + getRandomInt());
            fileChangeInfo.setCodeLine(getRandomInt());
            fileChangeInfos.add(fileChangeInfo);
        }
        return fileChangeInfos;
    }

}

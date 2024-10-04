package com.example.komfortsoft;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "MaxNumberController", description = "Controller for finding N-th maximum number in an Excel file")
@RestController
@RequestMapping("/api")
public class MaxNumberController {

    //http://localhost:52514/api/max-number?filePath=C:/path/test.xlsx&n=1
    @Operation(summary = "Find N-th maximum number in an Excel file")
    @ApiResponse(responseCode = "200", description = "N-th maximum number found",
            content = @Content(schema = @Schema(type = "integer")))
    @PostMapping("/max-number")
    public int findMaxNumber(@Parameter(description = "Path to the local Excel file") @RequestParam(value = "filePath") String filePath,
                             @Parameter(description = "N-th maximum number to find") @RequestParam(value = "n") int n) throws IOException {

        if (n <= 0) {
            throw new BadRequestException("n must be a positive integer");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found");
        }

        FileInputStream fileInputStream = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        List<Integer> numbers = new ArrayList<>();
        for (org.apache.poi.ss.usermodel.Row row : sheet) {
            org.apache.poi.ss.usermodel.Cell cell = row.getCell(0);
            if (cell != null) {
                switch (cell.getCellType()) {
                    case STRING:
                        String stringValue = cell.getStringCellValue();
                        // Обработка строкового значения
                        break;
                    case NUMERIC:
                        double numericValue = cell.getNumericCellValue();
                        numbers.add((int) numericValue);
                        break;
                    default:
                        // Обработка других типов значений
                        break;
                }
            }
        }

        int[] maxNumbers = new int[n];

        for (int number : numbers) {
            int i = 0;
            while (i < n && number < maxNumbers[i]) {
                i++;
            }
            if (i < n) {
                System.arraycopy(maxNumbers, i, maxNumbers, i + 1, n - i - 1);
                maxNumbers[i] = number;
            }
        }
        return maxNumbers[n - 1];
    }
}
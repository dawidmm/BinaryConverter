package pl.converter.converter;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.math.NumberUtils;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Controller {
    @FXML
    private TextField decimalField;
    @FXML
    private TextField binaryField;
    @FXML
    private TextArea textArea;
    @FXML
    private RadioButton radioNormal;
    @FXML
    private RadioButton radioUTwo;
    @FXML
    private RadioButton radioFloating;

    @FXML
    public void convert() {
        String decimal = decimalField.getText();
        String binary = binaryField.getText();
        if (radioFloating.isSelected()) {
            if (validateFloating(decimal, binary)) {
                {
                    boolean isNegative = false;
                    if (isBlank(decimal)) {
                        if (binary.charAt(0) == 49)
                            isNegative = true;
                        if (isNegative)
                            textArea.setText(String.valueOf(Double.longBitsToDouble(Long.parseLong(binary.substring(2), 2)) * (-1L)));
                        else
                            textArea.setText(String.valueOf(Double.longBitsToDouble(Long.parseLong(binary.substring(2), 2))));
                    } else {
                        if (Double.parseDouble(decimal) < 0.0D)
                            isNegative = true;
                        if (isNegative)
                            textArea.setText("1b" + Long.toBinaryString(Double.doubleToRawLongBits(Double.valueOf(decimal) * (-1D))));
                        else
                            textArea.setText("0b" + Long.toBinaryString(Double.doubleToRawLongBits(Double.valueOf(decimal))));
                    }
                }
            }
        } else if (radioNormal.isSelected()) {
            if (validate(decimal, binary)) {
                if (isNotBlank(decimal)) {
                    int decimalInt = Integer.parseInt(decimal);

                    if (decimalInt >= 0) {
                        textArea.setText(Integer.toBinaryString(decimalInt));
                    } else {
                        textArea.setText("Liczba musi być dodatnia");
                    }
                } else {
                    textArea.setText(String.valueOf(Long.parseLong(binary, 2)));
                }
            }
        } else if (radioUTwo.isSelected()) {
            if (validateFloating(decimal, binary)) {
                if (isNotBlank(decimal)) {
                    int decimalInt = Integer.parseInt(decimal);
                    boolean isNegative = false;

                    if (decimalInt < 0)
                            isNegative = true;

                    if (isNegative) {
                        decimalInt = decimalInt * (-1);
                        String binaryDecimal = Integer.toBinaryString(decimalInt);
                        binaryDecimal = binaryDecimal.replace("0", "2").replace("1", "0").replace("2", "1");
                        int binaryDecimalSize = binaryDecimal.length();
                        decimalInt = Integer.parseInt(binaryDecimal, 2) + 1;
                        binaryDecimal = Integer.toBinaryString(decimalInt);
                        if (binaryDecimal.length() != binaryDecimalSize) {
                            for (int i = 0; i<binaryDecimalSize-binaryDecimal.length(); i++) {
                                binaryDecimal = "0" + binaryDecimal;
                            }
                        }
                        textArea.setText("1b" + binaryDecimal);
                    } else {
                        String binaryDecimal = Integer.toBinaryString(decimalInt);
                        binaryDecimal = binaryDecimal.replace("0", "2").replace("1", "0").replace("2", "1");
                        int binaryDecimalSize = binaryDecimal.length();
                        decimalInt = Integer.parseInt(binaryDecimal, 2) + 1;
                        binaryDecimal = Integer.toBinaryString(decimalInt);
                        if (binaryDecimal.length() != binaryDecimalSize) {
                            for (int i = 0; i<binaryDecimalSize-binaryDecimal.length(); i++) {
                                binaryDecimal = "0" + binaryDecimal;
                            }
                        }
                        textArea.setText("0b" + binaryDecimal);
                    }
                } else {
                    boolean isNegative = false;

                    if (binary.charAt(0) == 49)
                        isNegative = true;

                    binary = binary.substring(2);

                    if (isNegative) {
                        int decimalInt = Integer.parseInt(binary, 2) - 1;
                        int binarySize = binary.length();
                        binary = Integer.toBinaryString(decimalInt);
                        if (binary.length() != binarySize) {
                            for (int i = 0; i<binarySize-binary.length(); i++) {
                                binary = "0" + binary;
                            }
                        }
                        binary = binary.replace("0", "2").replace("1", "0").replace("2", "1");
                        textArea.setText(String.valueOf(Integer.parseInt(binary, 2) * (-1)));
                    } else {
                        int decimalInt = Integer.parseInt(binary, 2) - 1;
                        int binarySize = binary.length();
                        binary = Integer.toBinaryString(decimalInt);
                        if (binary.length() != binarySize) {
                            for (int i = 0; i<binarySize-binary.length(); i++) {
                                binary = "0" + binary;
                            }
                        }
                        binary = binary.replace("0", "2").replace("1", "0").replace("2", "1");
                        textArea.setText(String.valueOf(Integer.parseInt(binary, 2)));
                    }
                }
            }
        }
    }

    private boolean validateFloating(String decimal, String binary) {
        if (isBlank(decimal) && isBlank(binary)) {
            textArea.setText("Pola są puste, wprowadź poprawne liczby");
            return false;
        } else if (isNotBlank(binary) && isNotBlank(decimal)) {
            textArea.setText("Oba pola nie mogą być uzupełnione");
            return false;
        } else if (isNotBlank(decimal)) {
            if (!NumberUtils.isCreatable(decimal)) {
                textArea.setText("Niepoprawna liczba w polu dziesiętnym");
                return false;
            }
            return true;
        } else {
            if (!NumberUtils.isCreatable(binary.substring(2)) || binary.contains("2") || binary.contains("3") || binary.contains("4") || binary.contains("5") || binary.contains("6") || binary.contains("7") || binary.contains("8") || binary.contains("9")) {
                if (!binary.startsWith("1b") || !binary.startsWith("0b")) {
                    textArea.setText("Niepoprawna liczba w polu binarnym");
                    return false;
                }
            }
            return true;
        }
    }

    private boolean validate(String decimal, String binary) {
        if (isBlank(decimal) && isBlank(binary)) {
            textArea.setText("Pola są puste, wprowadź poprawne liczby");
            return false;
        } else if (isNotBlank(binary) && isNotBlank(decimal)) {
            textArea.setText("Oba pola nie mogą być uzupełnione");
            return false;
        } else if (isNotBlank(decimal)) {
            if (!NumberUtils.isCreatable(decimal)) {
                textArea.setText("Niepoprawna liczba w polu dziesiętnym");
                return false;
            }
            return true;
        } else {
            if (!NumberUtils.isCreatable(binary.substring(2)) || binary.contains("2") || binary.contains("3") || binary.contains("4") || binary.contains("5") || binary.contains("6") || binary.contains("7") || binary.contains("8") || binary.contains("9")) {
                textArea.setText("Niepoprawna liczba w polu binarnym");
                return false;
            }
            return true;
        }
    }

    @FXML
    public void checkRadio1() {
        radioUTwo.setSelected(false);
        radioFloating.setSelected(false);
    }

    @FXML
    public void checkRadio2() {
        radioNormal.setSelected(false);
        radioFloating.setSelected(false);
    }

    @FXML
    public void checkRadio3() {
        radioNormal.setSelected(false);
        radioUTwo.setSelected(false);
    }
}
package gui.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class Utils {
	
	private static final String DATE_PATTERN = "dd/MM/yyyy";
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
	
	public static String formatDateToString(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }
	
	public static LocalDate parseStringToDate(String dateString) {
        try {
        	return LocalDate.parse(dateString, DATE_FORMATTER);
        } 
        catch (DateTimeParseException e) {
            return null;
        }
    }
	
	public static boolean dataValida(String dateString) {
    	return (parseStringToDate(dateString) != null);
    }
	
	
	public static Integer converterParaInt(String str) {
		try {
			return Integer.parseInt(str);
		} 
		catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Double converterParaDouble(String str) {
		try {
			return Double.parseDouble(str);
		} 
		catch (NumberFormatException e) {
			return null;
		}
	}

	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
		
	
	public static void formatDatePicker(DatePicker datePicker, String format) {
		datePicker.setConverter(new StringConverter<LocalDate>() {

			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);
			{
				datePicker.setPromptText(format.toLowerCase());
			}

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});
	}
	
	public static <T> void formatTableColumnDate(TableColumn<T, LocalDate> tableColumn) {
		tableColumn.setCellFactory(column -> {
			TableCell<T, LocalDate> cell = new TableCell<T, LocalDate>() {

				@Override
				protected void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText("");
					} else {						
						setText(formatDateToString(item));
					}
				}
			};
			return cell;
		});
	}
	
}

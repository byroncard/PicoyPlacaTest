package picoyplaca;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

public class PicoyPlaca {

	private String inputPlaca = "PCM5803";
	private String inputDate = "14/07/2020";
	private String inputTime = "07:30";

	@Test
	public void testPicoyPlaca() {
		assertEquals(true, havePicoPlaca(inputPlaca, inputDate, inputTime));
	}

	private boolean havePicoPlaca(String inputPlaca2, String inputDate2, String inputTime2) {
		Integer lastDigit = null;
		Boolean isPlate = Boolean.FALSE;
		Boolean isHour = Boolean.FALSE;
		try {
			lastDigit = Integer.valueOf(inputPlaca2.substring(6, 7));
			
			isPlate = getIsPlate(getListDay(),getDayOfWeek(inputDate2),lastDigit);
			isHour = getIsHour(inputTime2);
			
			if (isPlate && isHour)
				System.out.println("You have Pico y Placa");
			else
				System.out.println("You don't have Pico y Placa");
			
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return true;
	}

	private Boolean getIsHour(String inputTime2) throws ParseException {
		DateFormat formatTime = new SimpleDateFormat("HH:mm");
		Date startTimeMorning = formatTime.parse("07:00");
		Date finishTimeMorning = formatTime.parse("09:30");
		Date StartTimeAfternoon = formatTime.parse("16:00");
		Date finishTimeAfternoon = formatTime.parse("19:30");
		Date timeToCampare = formatTime.parse(inputTime2);
		Boolean timeMorning = timeToCampare.after(startTimeMorning) && timeToCampare.before(finishTimeMorning);
		Boolean timeAfetrnoon = timeToCampare.after(StartTimeAfternoon) && timeToCampare.before(finishTimeAfternoon);		
		return timeAfetrnoon || timeMorning;
	}

	@SuppressWarnings("rawtypes")
	private Boolean getIsPlate(Map<Integer, String> listDay, String dayOfWeek, Integer lastDigit) {
		Boolean isPlateOut = Boolean.FALSE;
		Iterator it = listDay.keySet().iterator();
		while (it.hasNext()) {
			Integer key = Integer.valueOf(it.next().toString());
			isPlateOut = (key == lastDigit) && (listDay.get(key) == dayOfWeek);
			if (isPlateOut)
				break;
		}
		return isPlateOut;
	}

	private String getDayOfWeek(String inputDate2) throws ParseException {
		int dayNumber = 0;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String[] days = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturdar" };
		Date inputDateParameter = null;

		inputDateParameter = format.parse(inputDate2);

		Calendar date = Calendar.getInstance();
		date.setTime(inputDateParameter);
		dayNumber = date.get(Calendar.DAY_OF_WEEK);
		return days[dayNumber - 1];
	}

	private Map<Integer, String> getListDay() {
		Map<Integer, String> listPlacas = new HashMap<Integer, String>();
		listPlacas.put(1, "Monday");
		listPlacas.put(2, "Monday");
		listPlacas.put(3, "Tuesday");
		listPlacas.put(4, "Tuesday");
		listPlacas.put(5, "Wednesday");
		listPlacas.put(6, "Wednesday");
		listPlacas.put(7, "Thursday");
		listPlacas.put(8, "Thursday");
		listPlacas.put(9, "Friday");
		listPlacas.put(0, "Friday");

		return listPlacas;

	}

}

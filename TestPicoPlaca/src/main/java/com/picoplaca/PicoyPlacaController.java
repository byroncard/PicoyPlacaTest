package com.picoplaca;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class PicoyPlacaController {

	private String inputPlaca;
	private String inputDate;
	private String inputTime;

	public void checkPicoPlaca() {
		try {
			System.out.println("Ingreso al metodo");
			FacesContext context = FacesContext.getCurrentInstance();
			System.out.println("Placa: "+getInputPlaca());
			System.out.println("Fecha: "+getInputDate());
			System.out.println("Hora: "+getInputTime());
			if (havePicoPlaca(getInputPlaca(), getInputDate(), getInputTime())){
				context.addMessage(null, new FacesMessage("Successful",  "You have Pico y Placa") );
			}
			else{
				context.addMessage(null, new FacesMessage("Successful",  "You don't have Pico y Placa") );
			}
			clearField();
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		}
	}

	private void clearField() {
		setInputDate(null);
		setInputPlaca(null);
		setInputTime(null);
		
	}

	public String getInputPlaca() {
		return inputPlaca;
	}

	public void setInputPlaca(String inputPlaca) {
		this.inputPlaca = inputPlaca;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public String getInputTime() {
		return inputTime;
	}

	public void setInputTime(String inputTime) {
		this.inputTime = inputTime;
	}

	private boolean havePicoPlaca(String inputPlaca2, String inputDate2, String inputTime2) {
		Integer lastDigit = null;
		Boolean isPlate = Boolean.FALSE;
		Boolean isHour = Boolean.FALSE;
		try {
			lastDigit = Integer.valueOf(inputPlaca2.substring(6, 7));

			isPlate = getIsPlate(getListDay(), getDayOfWeek(inputDate2), lastDigit);
			isHour = getIsHour(inputTime2);

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return isPlate && isHour;
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

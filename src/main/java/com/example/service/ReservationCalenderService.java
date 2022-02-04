package com.example.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.ReservationCalender;
import com.example.repository.ReservationCalenderMapper;

@Service
@Transactional
public class ReservationCalenderService {

	@Autowired
	private ReservationCalenderMapper reservationCalenderMapper;
	
	public List<ReservationCalender> searchReservedRoom(LocalDate startDate, LocalDate endDate, Integer roomId){
		
		List<ReservationCalender> reservationCalender 
		= reservationCalenderMapper.findReservableRoomById(startDate, endDate, roomId);
		
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		for(ReservationCalender day: reservationCalender) {
			if(day.getDate().isBefore(tomorrow)) {
				day.setEmpty(" ");
			}else {
				if(day.getReservedRoom() >= day.getReservationLimit()) {
					day.setEmpty("�~");
				}else {
					day.setEmpty("�Z");
				}
			}
		}
		
		//�J�����_�[�\���̂��ߌ����߂̋󔒂�}��
		int beforeBlank= startDate.getDayOfWeek().getValue() - 1;
		for(int i =0; i<=beforeBlank; i++ ) {
			ReservationCalender day = null;
			reservationCalender.add(0, day);
		}
		
		return reservationCalender;
	}
	
	public void updateReservationCalender(LocalDate date, Integer stayDays, Integer roomId) {
		reservationCalenderMapper.updateReservationCalender(date, stayDays, roomId);
	}

}

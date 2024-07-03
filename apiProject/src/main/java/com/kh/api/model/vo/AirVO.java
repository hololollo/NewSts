package com.kh.api.model.vo;

public class AirVO {
	
	private String pm10Value; // 미세먼지 농도
	private String stationName; // 측정소명
	private String dataTime; // 측정일시
	private String o3Value; // 오존 농도
	private String khaiValue; // 통합 대기 환경 수치
	public AirVO() {};
	public AirVO(String pm10Value, String stationName, String dataTime, String o3Value, String khaiValue) {
		super();
		this.pm10Value = pm10Value;
		this.stationName = stationName;
		this.dataTime = dataTime;
		this.o3Value = o3Value;
		this.khaiValue = khaiValue;
	}
	public String getPm10Value() {
		return pm10Value;
	}
	public void setPm10Value(String pm10Value) {
		this.pm10Value = pm10Value;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getDataTime() {
		return dataTime;
	}
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}
	public String getO3Value() {
		return o3Value;
	}
	public void setO3Value(String o3Value) {
		this.o3Value = o3Value;
	}
	public String getKhaiValue() {
		return khaiValue;
	}
	public void setKhaiValue(String khaiValue) {
		this.khaiValue = khaiValue;
	}
	@Override
	public String toString() {
		return "AirVO [pm10Value=" + pm10Value + ", stationName=" + stationName + ", dataTime=" + dataTime
				+ ", o3Value=" + o3Value + ", khaiValue=" + khaiValue + "]";
	}
	
	
}

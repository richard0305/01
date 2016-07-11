package com.tarena.allrun.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.tarena.allrun.R;
import com.tarena.allrun.util.BaiduMapUtil;

/**
 * Created by yanglijun 2016-6-30����11:26:02
 */
public class SportFragment extends Fragment {
	View view;
	// ��λ
	LocationClient locationClient;
	MapView mapView;
	// �����ͼ
	BaiduMap baiduMap;
	AlertDialog dialog;
	TextView tvAction;
	int count = 3;
	ArrayList<LatLng> positionList = new ArrayList();
	// ��ʾ�˶�ͳ��ʱ��
	Handler handler = new Handler();
	int sleepTime = 2000;
	Runnable runnable;
	LinearLayout linearLayout;

	
	@Override
	public void onPause() {
		try {
			mapView.onPause();
			locationClient.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onPause();
	}

	@Override
	public void onResume() {
		try {
			mapView.onResume();
			locationClient.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			view = inflater.inflate(R.layout.fragment_sport, null);

			tvAction = (TextView) view
					.findViewById(R.id.tv_fragment_sport_action);

			addListener();
			linearLayout = (LinearLayout) view
					.findViewById(R.id.ll_sport_recorder);
			mapView = (MapView) view.findViewById(R.id.mapView);
			baiduMap = mapView.getMap();

			// ��ͼ�����¼��������꣬��ʾͼ
			baiduMap.setOnMapClickListener(new OnMapClickListener() {

				@Override
				public boolean onMapPoiClick(MapPoi arg0) {
					return false;
				}

				@Override
				public void onMapClick(LatLng position) {
					Log.i("�Ҽҵ�����", position.latitude + "," + position.longitude);
					// moveMapCenter(position);
					// baiduMap.clear();
					// showImage(position);

					// ģ���û��˶�
					positionList.add(position);
					if (positionList.size() >= 2) {
						// ����markerOptions����
						PolylineOptions polylineOptions = new PolylineOptions();
						// �����õ��ĵ�
						polylineOptions.points(positionList);
						baiduMap.clear();
						baiduMap.addOverlay(polylineOptions);
					}

				}
			});

			locationClient = new LocationClient(getActivity());
			// ���ö�λ�Ĳ���
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);
			option.setCoorType("bd0911");
			// ÿ��2S��һ������
			// ʱ������1000��ֻ��һ��
			option.setScanSpan(1000);
			// �ٴ����¶�λ
			// locationClient.requestLocation();

			locationClient.setLocOption(option);

			// 2.4 �ðٶȵ�ͼ����еĽ���
			// BDLocationListener ָ��ʵ����
			MyBdLocationListener listener = new MyBdLocationListener();
			locationClient.registerLocationListener(listener);
			locationClient.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	private void addListener() {
		tvAction.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					String text = ((TextView) v).getText().toString();
					// �жϵ�����ʱ���ǿ�ʼ���ǽ���
					if ("����".equals(text)) {
						linearLayout.setVisibility(view.GONE);
						baiduMap.clear();
						positionList.clear();
						//
						handler.removeCallbacks(runnable);
						tvAction.setText("��ʼ");
						count = 3;
					} else {

						// ��xml���view
						final View view = View.inflate(getActivity(),
								R.layout.activity_show_counter, null);
						// ����dialog
						dialog = new AlertDialog.Builder(getActivity())
								.create();
						// Ϊdialog���ó�view
						dialog.setView(view);
						// ��ʾdialog
						dialog.show();

						// ��view���ҵ�textview
						final TextView tv = (TextView) view
								.findViewById(R.id.tv_show_count);
						// ÿ��2Sconut��1,����ʾ����
						final Handler handler = new Handler();
						handler.postDelayed(new Runnable() {

							@Override
							public void run() {
								tv.setText(String.valueOf(count));
								count = count - 1;
								if (count > -1) {
									handler.postDelayed(this, 2000);
								} else {
									dialog.dismiss();
									dialog = null;

									// ��ʾͳ�ƽ���
									showRecorder();

								}
							}

						}, 0);

						tvAction.setText("����");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	private void showRecorder() {
		linearLayout.setVisibility(view.VISIBLE);
		// �ҵ�ͳ�ǽ����ϵĿؼ�
		// meter��ͳ��ʱ��
		final Chronometer meter = (Chronometer) view
				.findViewById(R.id.chronometer1);
		meter.start();
		meter.setBase(SystemClock.elapsedRealtime());
		final TextView tvDistance = (TextView) view
				.findViewById(R.id.tv_distance);
		tvDistance.setText("0.00");
		final TextView tvSpeed = (TextView) view
				.findViewById(R.id.tv_recorder_speed);
		tvSpeed.setText("0.00");
		// ÿ�������������
		runnable = new Runnable() {

			@Override
			public void run() {
				try {
					double distance = 0;
					if (positionList.size() >= 2) {
						for (int i = 0; i < positionList.size() - 1; i++) {
							double long1 = positionList.get(i).longitude;
							double lat1 = positionList.get(i).latitude;
							double long2 = positionList.get(i + 1).longitude;
							double lat2 = positionList.get(i + 1).latitude;

							distance = distance
									+ BaiduMapUtil.GetDistance(long1, lat1,
											long2, lat2);
						}
						// ����ת�ɹ���
						distance = distance / 1000;
						String strDistance = String.valueOf(distance);
						if (strDistance.contains(".")) {
							int pointIndex = strDistance.indexOf(".");
							strDistance = strDistance.substring(0,
									pointIndex + 2);
						}
						tvDistance.setText(strDistance);
						// ��ʱ��

						String time = meter.getText().toString();
						// �÷���
						// [0]�ŵ���01 [1]�ŵ���48
						String[] array = time.split(":");
						double minute = Integer.parseInt(array[0]);
						// ����
						double second = Double.parseDouble(array[1]);
						// ����ת��Сʱ
						double hour = minute / 60 + second / 60 / 60;
						double speed = distance / hour;
						String strSpeed = String.valueOf(speed);
						if (strSpeed.contains(".")) {
							strSpeed = strSpeed.substring(0,
									strSpeed.indexOf(".") + 3);
						}
						tvSpeed.setText(strSpeed);
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					handler.postDelayed(this, sleepTime);
				}

			}
		};
		handler.postDelayed(runnable, sleepTime);

	}

	/**
	 * �ƶ���ͼ���ĵ�Ϊ��ǰλ�� status 17��ʾ��ͼ����ʾ����4-17
	 * 
	 * @param currentPosition
	 */
	private void moveMapCenter(LatLng currentPosition) {
		MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(
				currentPosition, 17);
		baiduMap.animateMapStatus(update);
	}

	/**
	 * �ڵ�ǰλ������ʾһ��ͼ
	 * 
	 * @param currentPosition
	 */
	private void showImage(LatLng currentPosition) {
		MarkerOptions options = new MarkerOptions();
		options.position(currentPosition);
		options.icon(BitmapDescriptorFactory
				.fromResource(R.drawable.icon_marka));
		baiduMap.addOverlay(options);
	}

	// ʵ����
	class MyBdLocationListener implements BDLocationListener {

		// �ٶȵ�ͼ��ܶ�λ�ɹ�����ýӿڣ��ӿ�ָ��ʵ���ࡣ
		@Override
		public void onReceiveLocation(BDLocation bdlocation) {
			try {
				// �þ���
				double longitude = bdlocation.getLongitude();
				double latitude = bdlocation.getLatitude();

				Log.i("����", "γ��=" + latitude + "����=" + latitude);
				// �жϳ�����û�еõ�����
				// �õ���4.9E-324��û�еõ����꣬����ԭ��
				// 1.�õ�ԭ��ģ����
				// 2.GPS�ºŲ���
				if (latitude == 4.9E-324) {

					// ģ��һ����ַ
					latitude = 39.876539;
					longitude = 116.464984;

				}

				// �ƶ���ͼ���ĵ�Ϊ��ǰλ��
				// status
				// 17��ʾ��ͼ����ʾ����4-17
				LatLng currentPosition = new LatLng(latitude, longitude);
				moveMapCenter(currentPosition);

				// �ڵ�ǰλ������ʾһ��ͼ
				showImage(currentPosition);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}

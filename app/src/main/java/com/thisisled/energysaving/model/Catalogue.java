package com.thisisled.energysaving.model;

import java.util.ArrayList;

import com.thisisled.energysaving.products.LED;
import com.thisisled.energysaving.products.Lighting;

import android.os.Parcel;
import android.os.Parcelable;


public class Catalogue implements Parcelable {
	private ArrayList<Lighting>	products; // change to hashmap?

	public Catalogue() {
		products = new ArrayList<Lighting>();
		initialise_products();
	}
	
	/**
	 * Catalogue Parcelable parameterised constructor.
	 * @param in (Parcel)
	 */
	public Catalogue(final Parcel in) {
		read_from_parcel(in);
	}

	private void initialise_products() {
		products.add(new LED("litePIPE600", 9, 28.5, 50000));
		products.add(new LED("litePIPE1200", 16, 39.5, 50000));
		products.add(new LED("litePIPE1500", 20, 45, 50000));
		products.add(new LED("litePIPE1800", 24, 52, 50000));
		products.add(new LED("liteRAFT600", 20, 199, 70000));
		products.add(new LED("liteRAFT1200", 40, 199, 70000));
		products.add(new LED("VAPOURlite1200", 40, 80, 50000));
		products.add(new LED("VAPOURlite1500", 50, 90, 50000));
	}
	
	/**
	 * Get's Lighting product from Catalogue using name as parameter.
	 * 
	 * @param name (String)
	 */
	public Lighting get_lighting_by_name(String name) {
		Lighting led = null;
		for (int i = 0; i < products.size(); i++) {
			Lighting check = products.get(i);
			if(check.getName().equalsIgnoreCase(name)) {
				led = check;
				break;
			}
		}
		return led;
	}
	
	// ***** PARCELABLE implementation *****//
	
	@SuppressWarnings("unchecked")
	private void read_from_parcel(final Parcel in) {
		products = in.readArrayList(Lighting.class.getClassLoader());
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		dest.writeList(products);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator	CREATOR	= new Parcelable.Creator() {
		
		public Catalogue createFromParcel(final Parcel in) {
			return new Catalogue(in);
		}

		public Catalogue[] newArray(final int size) {
			return new Catalogue[size];
		}

	};

	// ***** SETTERS & GETTERS *****//
	

	public final ArrayList<Lighting> getProducts() {
		return products;
	}

	public final void setProducts(ArrayList<Lighting> products) {
		this.products = products;
	}

}
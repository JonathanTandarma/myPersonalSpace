package com.gp.DTO;

import com.gp.entity.AksesorisDetail;
import com.gp.entity.JoranDetail;
import com.gp.entity.KailDetail;
import com.gp.entity.LineDetail;
import com.gp.entity.ReelDetail;

// public class InsertNewProductDTO extends JoranDetail,ReelDetail,dll. {}

public class InsertNewProductDTO {
	private String productName;
	private int productId;
	private int merkId;
	private int kategoriId;
	
	private JoranDetail joranDetail;
	private ReelDetail reelDetail;
	private LineDetail lineDetail;
	private KailDetail kailDetail;
	private AksesorisDetail aksesorisDetail;
	
	public AksesorisDetail getAksesorisDetail() {
		return aksesorisDetail;
	}
	public void setAksesorisDetail(AksesorisDetail aksesorisDetail) {
		this.aksesorisDetail = aksesorisDetail;
	}
	public JoranDetail getJoranDetail() {
		return joranDetail;
	}
	public void setJoranDetail(JoranDetail joranDetail) {
		this.joranDetail = joranDetail;
	}
	public ReelDetail getReelDetail() {
		return reelDetail;
	}
	public void setReelDetail(ReelDetail reelDetail) {
		this.reelDetail = reelDetail;
	}
	public LineDetail getLineDetail() {
		return lineDetail;
	}
	public void setLineDetail(LineDetail lineDetail) {
		this.lineDetail = lineDetail;
	}
	public KailDetail getKailDetail() {
		return kailDetail;
	}
	public void setKailDetail(KailDetail kailDetail) {
		this.kailDetail = kailDetail;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getMerkId() {
		return merkId;
	}
	public void setMerkId(int merkId) {
		this.merkId = merkId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getKategoriId() {
		return kategoriId;
	}
	public void setKategoriId(int kategoriId) {
		this.kategoriId = kategoriId;
	}
	
	
	
}

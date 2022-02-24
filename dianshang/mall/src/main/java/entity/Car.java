package entity;

public class Car {
    private String goods_id;
    private String gmt_create;
    private String gmt_update;
    private String userid;
    private String num;

   public String getGoods_id() {
      return goods_id;
   }

   public void setGoods_id(String goods_id) {
      this.goods_id = goods_id;
   }

   public String getGmt_create() {
      return gmt_create;
   }

   public void setGmt_create(String gmt_create) {
      this.gmt_create = gmt_create;
   }

   public String getGmt_update() {
      return gmt_update;
   }

   public void setGmt_update(String gmt_update) {
      this.gmt_update = gmt_update;
   }

   public String getUserid() {
      return userid;
   }

   public void setUserid(String userid) {
      this.userid = userid;
   }

   public String getNum() {
      return num;
   }

   public void setNum(String num) {
      this.num = num;
   }
}

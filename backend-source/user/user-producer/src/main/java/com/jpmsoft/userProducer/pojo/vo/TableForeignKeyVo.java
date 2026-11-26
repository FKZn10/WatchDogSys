package com.jpmsoft.userProducer.pojo.vo;

public class TableForeignKeyVo {


   private  String  fkOwner;
   private  String fkTable;
   private  String fk;
   private  String fkCol;
   private  String pkOwner;
   private  String pkTable;
   private  String pkCol;

    public String getFkOwner() {
        return fkOwner;
    }

    public void setFkOwner(String fkOwner) {
        this.fkOwner = fkOwner;
    }

    public String getFkTable() {
        return fkTable;
    }

    public void setFkTable(String fkTable) {
        this.fkTable = fkTable;
    }

    public String getFk() {
        return fk;
    }

    public void setFk(String fk) {
        this.fk = fk;
    }

    public String getFkCol() {
        return fkCol;
    }

    public void setFkCol(String fkCol) {
        this.fkCol = fkCol;
    }

    public String getPkOwner() {
        return pkOwner;
    }

    public void setPkOwner(String pkOwner) {
        this.pkOwner = pkOwner;
    }

    public String getPkTable() {
        return pkTable;
    }

    public void setPkTable(String pkTable) {
        this.pkTable = pkTable;
    }

    public String getPkCol() {
        return pkCol;
    }

    public void setPkCol(String pkCol) {
        this.pkCol = pkCol;
    }
}

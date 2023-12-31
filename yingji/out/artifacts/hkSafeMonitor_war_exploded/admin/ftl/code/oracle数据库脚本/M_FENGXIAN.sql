-- ----------------------------
-- Table structure for "C##FHADMIN"."M_FENGXIAN"
-- ----------------------------
-- DROP TABLE "C##FHADMIN"."M_FENGXIAN";
CREATE TABLE "C##FHADMIN"."M_FENGXIAN" (
	"FENGXIAN_ID" VARCHAR2(255 BYTE) NULL ,
	"ISDEL" NUMBER(10) NULL ,
	"SORT" NUMBER(10) NULL ,
	"CREATER" VARCHAR2(255 BYTE) NULL ,
	"CREATE_DATE" VARCHAR2(255 BYTE) NULL ,
	"MODIFYER" VARCHAR2(255 BYTE) NULL ,
	"MODIFY_DATE" VARCHAR2(255 BYTE) NULL ,
	"SECOND_UNIT" VARCHAR2(255 BYTE) NULL ,
	"THIRD_UNIT" VARCHAR2(255 BYTE) NULL ,
	"FENGXIAN_ADDRESS" VARCHAR2(255 BYTE) NULL ,
	"FENGXIAN_AREA" VARCHAR2(255 BYTE) NULL ,
	"FENGXIAN_HAZARD" VARCHAR2(255 BYTE) NULL ,
	"FENGXIAN_ACCIDENT_TYPE" VARCHAR2(255 BYTE) NULL ,
	"FENGXIAN_LEVEL" VARCHAR2(255 BYTE) NULL ,
	"CONTROL_MEASURE" VARCHAR2(255 BYTE) NULL ,
	"EMERGENCY_MEASURE" VARCHAR2(255 BYTE) NULL ,
	"HAZARD_DURATION" VARCHAR2(255 BYTE) NULL ,
	"MANAGEMENT_LEVEL" VARCHAR2(255 BYTE) NULL ,
	"RESPONSIBILITY_UNIT" VARCHAR2(255 BYTE) NULL ,
	"RESPONSIBILITY_PEOPLE" VARCHAR2(255 BYTE) NULL ,
	"RESPONSIBILITY_PHONE" VARCHAR2(255 BYTE) NULL ,
	"RECOGNITION_TIME" VARCHAR2(255 BYTE) NULL ,
	"DURANTION_CIRCLE" VARCHAR2(255 BYTE) NULL ,
	"M_FENGXIAN_ID" VARCHAR2(100 BYTE) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;

COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."FENGXIAN_ID" IS '风险id';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."ISDEL" IS '是否删除1删除0未删除';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."SORT" IS '排序';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."CREATER" IS '创建人';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."CREATE_DATE" IS '创建时间';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."MODIFYER" IS '修改人';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."MODIFY_DATE" IS '修改时间';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."SECOND_UNIT" IS '二级公司';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."THIRD_UNIT" IS '三级机构';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."FENGXIAN_ADDRESS" IS '地址';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."FENGXIAN_AREA" IS '区域';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."FENGXIAN_HAZARD" IS '危险源';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."FENGXIAN_ACCIDENT_TYPE" IS '可能导致的事故类型';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."FENGXIAN_LEVEL" IS '风险等级';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."CONTROL_MEASURE" IS '控制措施';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."EMERGENCY_MEASURE" IS '应急措施';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."HAZARD_DURATION" IS '危险源持续时间';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."MANAGEMENT_LEVEL" IS '管理层级';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."RESPONSIBILITY_UNIT" IS '责任单位(组织机构)';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."RESPONSIBILITY_PEOPLE" IS '责任人';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."RESPONSIBILITY_PHONE" IS '责任人联系方式';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."RECOGNITION_TIME" IS '识别时间';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."DURANTION_CIRCLE" IS '持续周期';
COMMENT ON COLUMN "C##FHADMIN"."M_FENGXIAN"."M_FENGXIAN_ID" IS 'ID';

-- ----------------------------
-- Indexes structure for table M_FENGXIAN
-- ----------------------------

-- ----------------------------
-- Checks structure for table "C##FHADMIN"."M_FENGXIAN"

-- ----------------------------

ALTER TABLE "C##FHADMIN"."M_FENGXIAN" ADD CHECK ("M_FENGXIAN_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table "C##FHADMIN"."M_FENGXIAN"
-- ----------------------------
ALTER TABLE "C##FHADMIN"."M_FENGXIAN" ADD PRIMARY KEY ("M_FENGXIAN_ID");

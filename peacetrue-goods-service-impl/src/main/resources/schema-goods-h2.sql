drop table if exists goods;
create table goods
(
    id            bigint unsigned primary key auto_increment,
    cover_image   VARCHAR(500)              NOT NULL COMMENT '封面图片. 多个之间使用,分割',
    cover_video   VARCHAR(255)              NOT NULL default '' COMMENT '封面视频. 多个之间使用,分割',
    name          VARCHAR(255)              NOT NULL COMMENT '名称',
    detail        mediumtext                NOT NULL COMMENT '详情. 富文本',
    display       enum ('ONLINE','OFFLINE') NOT NULL COMMENT '展示状态. 上架、下架',
    price         decimal(11, 2) unsigned   NOT NULL COMMENT '价格(元). 最大到亿，保留 2 位小数',
    remark        VARCHAR(255)              NOT NULL default '' COMMENT '备注',
    serial_number bigint unsigned           not null comment '序号',
    creator_id    bigint unsigned           not null comment '创建者. 主键',
    created_time  datetime                  not null comment '创建时间',
    modifier_id   bigint unsigned           not null comment '最近修改者. 主键',
    modified_time timestamp                 not null comment '最近修改时间'
);

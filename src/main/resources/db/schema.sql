# v0.1.3
USE todolist;

DROP TABLE IF EXISTS user;

CREATE TABLE user (
  id MEDIUMINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR (100) NOT NULL,
  name VARCHAR (60) NOT NULL,
  password VARCHAR (60) NOT NULL,
  mobile VARCHAR (30) NOT NULL DEFAULT "",
  location VARCHAR (30) NOT NULL DEFAULT "",
  avatar VARCHAR (150) NOT NULL DEFAULT "",
  wechatid VARCHAR (50) NOT NULL DEFAULT "",
  enabled BOOLEAN NOT NULL DEFAULT TRUE,
  admin BOOLEAN NOT NULL DEFAULT FALSE,
  createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO user (email, name, password, admin, mobile, location, wechatid, avatar) VALUES("nonocast@gmail.com", "Hui", "$2a$10$NvR/yNovADZ2OQiipGUS7On7G0mKDknKzh5oWqGznv.qUIKitN/SC", TRUE, "13817122600", "上海", "oLwYvwad8ofhOc9yHK1WDbfH0mms", "http://wx.qlogo.cn/mmopen/KetjXWSVpptEZctGOzooiaFJCY3d6QV4icImfTyQVEiaibdTrMWlzjE0Bial1LKMkDBaPztNhf5EE9YgsFzBDH6LGDA/0");
INSERT INTO user (email, name, password, admin) VALUES("fyx@gmail.com", "fangyx", "$2a$10$NvR/yNovADZ2OQiipGUS7On7G0mKDknKzh5oWqGznv.qUIKitN/SC", FALSE);
INSERT INTO user (email, name, password, admin) VALUES("gexi7n@gmail.com", "Ge", "$2a$10$NvR/yNovADZ2OQiipGUS7On7G0mKDknKzh5oWqGznv.qUIKitN/SC", TRUE);
INSERT INTO user (email, name, password, admin) VALUES("naodaixiaoxiao@qq.com", "李毓洁", "$2a$10$NvR/yNovADZ2OQiipGUS7On7G0mKDknKzh5oWqGznv.qUIKitN/SC", FALSE);

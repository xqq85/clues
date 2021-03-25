# clues
客户线索跟踪管理系统
## 系统描述
##### 客户线索跟踪管理系统是一套企业管理客户及潜在客户的信息化管理系统，该项目分为用户管理、客户管理、线索管理、公海管理、订单管理 5 个模块。5 个模块都实现了模糊分页查询；用户管理实现登录、用户录入、权限管理；客户管理、线索管理、公海管理实现线索批量录入、线索跟踪管理以及消息推送。该系统采用 MVC 分层设计整合 SSM 框架，使用SpringSecurity 框架实现权限管理，前端管理平台使用 AdminLTE 快速开发；该项目使用 Redis 做缓存服务器，减少数据库访问压力；该系统具有高可用性，高并发性，系统采用 Nginx 反向代理服务器实现负载均衡，采用 Kafka 集群+邮箱信息推送微服务集群实现邮箱信息推送服务，使用 SpringBoot 快速搭建邮箱信息推送微服务， 邮箱信息推送微服务采用集群形式提高消息消费能力。
## 系统功能模块
### 1、登录/注销
###### 登录页面
![image](https://github.com/xqq85/clues/blob/main/imgs/fe18e31d9ac816c7d2b510f2ce1ebb1.png)
###### 注销页面
![image](https://github.com/xqq85/clues/blob/main/imgs/a4e05cc6ab51163dc584d9e7603b205.png)



### 2、系统管理分为三个部分：用户管理、角色管理、资源权限管理
##### 2.1 用户管理用于管理用户的基本信息，包括新建用户、修改用户、添加角色功能
###### 用户信息页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/1933ef754d1a542efdf1ad28781f788.png)
###### 新建用户页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/46bc12b9c782f5e71adf080fd0a7fa2.png)
###### 修改用户页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/2b473a50ba6486d77ddaf19c7e913d8.png)
###### 添加角色页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/c9b47470b673e3bb3775f5effbcfded.png)

##### 2.2 角色管理用于管理系统用户的角色，包括新建、删除、添加权限以及详细信息
###### 角色页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/ba8385142376a1dcbaee92a9de6b934.png)
###### 新建角色页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/135572d19081c5f81311d08284e656a.png)
###### 添加权限页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/c1a22a9284238eeb6647a4c3dac7bda.png)
###### 详细信息页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/80fed5ad9e6eb0ed001e92e3cf9c0b4.png)
##### 2.3 资源权限用于管理系统用户角色相关的权限，包括新建、删除以及详细信息
###### 资源权限页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/0f924fecb84acf045602efcd7fb0a57.png)
###### 新建页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/d822704a9047ce424441b3133d29abd.png)
###### 详细信息页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/706336296a1c2d8b6c23e0b2641ae5e.png)



### 3、客户线索管理分为三部分：线索管理、客户管理、公海管理
##### 3.1 线索管理用于管理线索基本信息，包括新建线索、修改线索、从EXCEL批量导入线索、向线索推送邮箱消息、将线索转换为客户
###### 线索信息页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/7eb1079d1480627484f165954a50c05.png)
###### 新建线索页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/196049170c2029af5c5d12786b133fd.png)
###### 修改线索页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/1d2bfb77f92240e4d2e8cddced67abc.png)
###### 从EXCEL批量导入线索页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/bd6b3dc71ec58fbccead4905f8e6cc9.png)
###### 向线索推送邮箱消息页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/4b9aecdd4d225b983235bc8e5637f0a.png)
###### 将线索转换为客户页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/75c7f4186609efa23b87df21a1b7ee7.png)
![avator](https://github.com/xqq85/clues/tree/main/imgs/98ba482cfe70222491b78852e1fa195.png)

##### 3.2 客户管理用于管理客户基本信息，包括修改客户、向客户推送邮箱消息、将客户转换为公海
###### 修改客户页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/f5b1422e51675d8b7e4f06e8f6938c8.png)
###### 向客户推送邮箱消息页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/99c40270d922178e751efffd172b159.png)
###### 将客户转换为公海
![avator](https://github.com/xqq85/clues/tree/main/imgs/20e5622d7d17d3c1331a9c6988d9c59.png)

##### 3.3 公海管理用于管理公海相关信息，包括新建、更改以及转化
![avator](https://github.com/xqq85/clues/tree/main/imgs/40162103ad289b30a7fb3a93454714d.png)
###### 新建页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/0b30a9697bc1decd7bc227abf909660.png)
###### 更改页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/38afcaef362ca514b7d2d4ffc0ebf3c.png)
###### 转化页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/3721ee644704cf64004c787ebb096b4.png)



### 4、产品/订单管理分为两部分：产品管理、订单管理
##### 4.1 产品管理用于管理公司具有的相关产品，包括新建产品、编辑产品以及客户下单
###### 新建产品页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/f0935e889d0645d0aafcc6b08d5d633.png)
###### 编辑产品页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/5ce0660e878fb77920dc847b813ade1.png)
###### 客户下单页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/023d05543bc47d32460e783d69eb240.png)

##### 4.2 订单管理用于管理客户的订单，包括查看页面、导出到EXCEL页面
###### 查看页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/3a492a2f55caa9807c6d2bf358c00e5.png)
###### 导出到EXCEL页面页面
![avator](https://github.com/xqq85/clues/tree/main/imgs/dc105816b34407573771c9330c309a0.png)

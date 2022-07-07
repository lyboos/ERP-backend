# 关于SQL中数据类型和部分转化的定义文件：
1.各种sheet的create_time:datetime,形式yyyy-MM--dd HH-mm-ss  
2.银行账户id/bank_account_id:varchar(31)  
3.操作员operator:varchar(255)  
4.各种sheet的状态state:varchar(31)  
5.策略类的类型为varchar(31)  
6.涉及金额的统一类型为decimal(10,2),对应Java中Bigdecimal类型  
7.生日一律使用date类型,形式yyyy-MM-dd  

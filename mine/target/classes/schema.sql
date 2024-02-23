
CREATE TABLE IF NOT EXISTS Deals (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    type_Id BIGINT NOT NULL,
    ticker VARCHAR(255) NOT NULL,
    order_Number VARCHAR(255) NOT NULL,
    deal_Number VARCHAR(255) NOT NULL,
    deal_Quantity INTEGER not null,
    deal_Price NUMERIC(20, 2) not null,
    deal_Total_Cost NUMERIC(20, 2) not null,
    deal_Trader VARCHAR(255) NOT NULL,
    deal_Commission NUMERIC(20, 2) not null
);

create table if not exists Deal_Type (
    id bigint primary key auto_increment,
    type varchar(255) not null
);




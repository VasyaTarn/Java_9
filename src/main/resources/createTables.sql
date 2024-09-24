CREATE TABLE IF NOT EXISTS public.ItemTypes (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.MenuItems (
    item_id SERIAL PRIMARY KEY,
    name_en VARCHAR(100) NOT NULL,
    name_other VARCHAR(100) NOT NULL,
    item_type_id INT NOT NULL REFERENCES public.ItemTypes(id),
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.Position (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.Staff (
    staff_id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    patronymic VARCHAR(100),
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(100),
    position_id INT NOT NULL REFERENCES public.Position(id)
);

CREATE TABLE IF NOT EXISTS public.Customers (
    customer_id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    patronymic VARCHAR(100),
    birth_date DATE NOT NULL,
    phone VARCHAR(15),
    email VARCHAR(100),
    discount DECIMAL(5, 2) DEFAULT 0
);

CREATE TABLE IF NOT EXISTS public.WorkSchedule (
    schedule_id SERIAL PRIMARY KEY,
    staff_id INT REFERENCES Staff(staff_id),
    work_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL
);

CREATE TABLE IF NOT EXISTS public.Orders (
    order_id SERIAL PRIMARY KEY,
    customer_first_name VARCHAR(50),
    customer_last_name VARCHAR(50),
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.OrderItems (
    order_item_id SERIAL PRIMARY KEY,
    order_id INT REFERENCES Orders(order_id),
    item_id INT REFERENCES MenuItems(item_id),
    quantity INT NOT NULL CHECK (quantity > 0),
    price DECIMAL(10, 2) NOT NULL
);
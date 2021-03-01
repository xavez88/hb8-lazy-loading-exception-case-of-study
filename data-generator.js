const fs = require('fs');
const NUMBER_USERS = 500;
const ORDERS_PER_USER = 500;

const generateData = async () => {

    let order_id = 0;
    for (let i = 1; i <= NUMBER_USERS; i++) {
        const userQuery = `INSERT INTO users(user_id, first_name, last_name, birth_date) VALUES (${i}, 'First-name-${i}', 'Last-name-${i}', NOW());\n`;
        fs.writeFileSync('./data-migration/test-data.sql', userQuery, { flag: 'a+' });

        for (let j = 1; j <= ORDERS_PER_USER; j++) {
            order_id++;
            const ordersQuery = `INSERT INTO orders(order_id,description,created_at,user_id) VALUES (${order_id}, 'Order-${i}-${j}', NOW(), ${i});\n`;
            fs.writeFileSync('./data-migration/test-data.sql', ordersQuery, { flag: 'a+' });
        }
    }

}

generateData();
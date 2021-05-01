ALTER TABLE products
ADD COLUMN product_type VARCHAR(10);

UPDATE products
SET product_type = 'WHOLE_CAKE'
WHERE product_id = 'bfc';

UPDATE products
SET product_type = 'WHOLE_CAKE'
WHERE product_id = 'cc';

UPDATE products
SET product_type = 'WHOLE_CAKE'
WHERE product_id = 'rsc';

UPDATE products
SET product_type = 'WHOLE_CAKE'
WHERE product_id = 'rv';

UPDATE products
SET product_type = 'WHOLE_CAKE'
WHERE product_id = 'scc';

UPDATE products
SET product_type = 'WHOLE_CAKE'
WHERE product_id = 'vsc';

ALTER TABLE products
ALTER COLUMN product_type SET NOT NULL;
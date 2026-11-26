-- Use the same local database as schema.sql and seed.sql.
-- Keep the filled-in copy in private/camera.local.sql.
SET NAMES utf8mb4;
SET @camera_id = NULL;
SET @device_serial = NULL;
SET @channel_no = '1';
SET @app_key = NULL;
SET @app_secret = NULL;

UPDATE smart_site_camera
SET Ssc_Serial = @device_serial,
    Ssc_Channel_No = @channel_no,
    Ssc_Code = CONCAT('ezopen://open.ys7.com/', @device_serial, '/', @channel_no, '.hd.live'),
    Ssc_Key = @app_key,
    Ssc_Secret = @app_secret
WHERE Ssc_Id = @camera_id
  AND Ssc_Kind = '11'
  AND NULLIF(@device_serial, '') IS NOT NULL
  AND NULLIF(@app_key, '') IS NOT NULL
  AND NULLIF(@app_secret, '') IS NOT NULL;

SELECT ROW_COUNT() AS updated_camera_count;

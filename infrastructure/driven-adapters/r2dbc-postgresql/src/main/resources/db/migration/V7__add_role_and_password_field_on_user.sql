DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name='users' AND column_name='password'
    ) THEN
        ALTER TABLE users ADD COLUMN password VARCHAR(255) NOT NULL;
    END IF;
END $$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name='users' AND column_name='role_id'
    ) THEN
        ALTER TABLE users ADD COLUMN role_id UUID NOT NULL;
    END IF;
END $$;

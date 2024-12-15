-- Insertion des thèmes
INSERT INTO theme (name, description)
VALUES (
        'Frontend',
        'Développement frontend avec HTML, CSS, JavaScript'
    ),
    ('Backend', 'Développement backend et APIs'),
    ('DevOps', 'Pratiques DevOps et déploiement'),
    (
        'Mobile',
        'Développement d''applications mobiles'
    ),
    ('Database', 'Bases de données et optimisation');
-- Création d'un utilisateur pour les articles
INSERT INTO users (email, username, password)
VALUES (
        'admin@mdd.com',
        'admin',
        '$2a$10$uS/GmX6mUoYJCGQlx8AYXuLlPF1hg5pZYE8fE3h8PsBAV7f0MCM2q'
    ),
    -- mot de passe: Admin123!
    (
        'user1@mdd.com',
        'user1',
        '$2a$10$uS/GmX6mUoYJCGQlx8AYXuLlPF1hg5pZYE8fE3h8PsBAV7f0MCM2q'
    );
-- mot de passe: Admin123!
-- Récupération des IDs
SET @admin_id = (
        SELECT id
        FROM users
        WHERE username = 'admin'
    );
SET @user1_id = (
        SELECT id
        FROM users
        WHERE username = 'user1'
    );
SET @frontend_id = (
        SELECT id
        FROM theme
        WHERE name = 'Frontend'
    );
SET @backend_id = (
        SELECT id
        FROM theme
        WHERE name = 'Backend'
    );
SET @devops_id = (
        SELECT id
        FROM theme
        WHERE name = 'DevOps'
    );
SET @mobile_id = (
        SELECT id
        FROM theme
        WHERE name = 'Mobile'
    );
SET @database_id = (
        SELECT id
        FROM theme
        WHERE name = 'Database'
    );
-- Insertion des articles
INSERT INTO article (title, content, created_at, author_id, theme_id)
VALUES (
        'Introduction à React',
        'React est une bibliothèque JavaScript pour créer des interfaces utilisateur...',
        NOW() - INTERVAL 10 DAY,
        @admin_id,
        @frontend_id
    ),
    (
        'Les bases de Spring Boot',
        'Spring Boot simplifie le développement d''applications Java...',
        NOW() - INTERVAL 9 DAY,
        @admin_id,
        @backend_id
    ),
    (
        'Docker pour les débutants',
        'Docker est une plateforme de conteneurisation...',
        NOW() - INTERVAL 8 DAY,
        @admin_id,
        @devops_id
    ),
    (
        'Développer avec Flutter',
        'Flutter permet de créer des applications mobiles multiplateformes...',
        NOW() - INTERVAL 7 DAY,
        @admin_id,
        @mobile_id
    ),
    (
        'Optimisation MySQL',
        'Techniques d''optimisation pour les bases de données MySQL...',
        NOW() - INTERVAL 6 DAY,
        @admin_id,
        @database_id
    ),
    (
        'JavaScript moderne',
        'Les nouvelles fonctionnalités de JavaScript ES6+...',
        NOW() - INTERVAL 5 DAY,
        @admin_id,
        @frontend_id
    ),
    (
        'API REST avec Node.js',
        'Créer des APIs REST performantes avec Node.js...',
        NOW() - INTERVAL 4 DAY,
        @admin_id,
        @backend_id
    ),
    (
        'CI/CD avec Jenkins',
        'Mettre en place un pipeline CI/CD avec Jenkins...',
        NOW() - INTERVAL 3 DAY,
        @admin_id,
        @devops_id
    ),
    (
        'React Native vs Flutter',
        'Comparaison des frameworks de développement mobile...',
        NOW() - INTERVAL 2 DAY,
        @admin_id,
        @mobile_id
    ),
    (
        'NoSQL avec MongoDB',
        'Introduction aux bases de données NoSQL...',
        NOW() - INTERVAL 1 DAY,
        @admin_id,
        @database_id
    );
-- Insertion des commentaires
INSERT INTO comment (content, created_at, author_id, article_id)
VALUES (
        'Super article ! Très utile pour les débutants.',
        NOW() - INTERVAL 9 DAY,
        @user1_id,
        (
            SELECT id
            FROM article
            WHERE title = 'Introduction à React'
        )
    ),
    (
        'J''utilise Spring Boot depuis 2 ans, c''est vraiment un excellent framework.',
        NOW() - INTERVAL 8 DAY,
        @user1_id,
        (
            SELECT id
            FROM article
            WHERE title = 'Les bases de Spring Boot'
        )
    ),
    (
        'Docker a vraiment révolutionné le déploiement.',
        NOW() - INTERVAL 7 DAY,
        @admin_id,
        (
            SELECT id
            FROM article
            WHERE title = 'Docker pour les débutants'
        )
    ),
    (
        'Flutter est prometteur, mais React Native reste solide.',
        NOW() - INTERVAL 6 DAY,
        @user1_id,
        (
            SELECT id
            FROM article
            WHERE title = 'Développer avec Flutter'
        )
    ),
    (
        'Les index sont vraiment importants pour les performances.',
        NOW() - INTERVAL 5 DAY,
        @admin_id,
        (
            SELECT id
            FROM article
            WHERE title = 'Optimisation MySQL'
        )
    ),
    (
        'ES6+ a rendu JavaScript beaucoup plus agréable.',
        NOW() - INTERVAL 4 DAY,
        @user1_id,
        (
            SELECT id
            FROM article
            WHERE title = 'JavaScript moderne'
        )
    ),
    (
        'Express.js est un must pour Node.js.',
        NOW() - INTERVAL 3 DAY,
        @admin_id,
        (
            SELECT id
            FROM article
            WHERE title = 'API REST avec Node.js'
        )
    ),
    (
        'Jenkins est très puissant mais la configuration peut être complexe.',
        NOW() - INTERVAL 2 DAY,
        @user1_id,
        (
            SELECT id
            FROM article
            WHERE title = 'CI/CD avec Jenkins'
        )
    ),
    (
        'Bon comparatif, ça aide à faire un choix éclairé.',
        NOW() - INTERVAL 1 DAY,
        @admin_id,
        (
            SELECT id
            FROM article
            WHERE title = 'React Native vs Flutter'
        )
    ),
    (
        'MongoDB est parfait pour les données non structurées.',
        NOW(),
        @user1_id,
        (
            SELECT id
            FROM article
            WHERE title = 'NoSQL avec MongoDB'
        )
    );
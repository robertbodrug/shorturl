INSERT INTO users (username, password, role) VALUES
        ('user1', '{noop}user1', 'user'),
        ('user2', '{noop}user2', 'user'),
        ('admin1', '{noop}admin1', 'admin'),
        ('user3', '{noop}user3', 'user');

INSERT INTO urls (id, short_url, long_url, score, is_active, username) VALUES
        (1, 'http://short.url/abc', 'http://very-long-url.com/this-is-a-very-long-url', 100, TRUE, 'user1'),
        (2, 'http://short.url/def', 'http://another-long-url.com/this-is-another-long-url', 50, TRUE, 'user2'),
        (3, 'http://short.url/ghi', 'http://yet-another-long-url.com/this-is-yet-another-long-url', 75, TRUE, 'admin1'),
        (4, 'http://short.url/jkl', 'http://short-url.com/short-url', 25, FALSE, 'guest1');


DROP TABLE IF EXISTS JKEINTERESTRATE;

CREATE TABLE JKEINTERESTRATE (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  category VARCHAR(10) NOT NULL,
  tenure VARCHAR(50) NOT NULL,
  interestRateRegular VARCHAR(10) NOT NULL,
  interestRateSenior VARCHAR(10) NOT NULL,
  comments VARCHAR(50)
);

INSERT INTO JKEINTERESTRATE
    (category, tenure, interestRateRegular, interestRateSenior, comments) VALUES
    ('FD', '7 days to 45 days', '5.75%', '6.25%', ''),
    ('FD', '46 days to 179 days', '6.25%', '6.75%', ''),
    ('FD', '180 days to 210 days', '6.35%', '6.85%', ''),
    ('FD', '211 days to 364 days', '6.40%', '6.90%', ''),
    ('FD', '1 year to 2 years', '6.80%', '7.30%', ''),
    ('FD', '2 years to 3 years', '6.80%', '7.30%', ''),
    ('FD', '3 years to 4 years', '6.80%', '7.30%', ''),
    ('FD', '5 years to 10 years', '6.85%', '7.35%', ''),
    ('RD', '1 Year', '6.70%', '7.00%', ''),
    ('RD', '2 Years', '6.75%', '7.05%', ''),
    ('RD', '3 Years', '6.80%', '7.10%', ''),
    ('RD', '4 Years', '6.80%', '7.10%', ''),
    ('RD', '5 Years', '6.85%', '7.15%', ''),
    ('RD', '5+ Years', '6.85%', '7.15%', ''),
    ('SB', '', '4.00%', '4.15%', '');

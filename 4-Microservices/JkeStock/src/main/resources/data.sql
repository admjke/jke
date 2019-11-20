DROP TABLE IF EXISTS JkeStockQuote;

CREATE TABLE JkeStockQuote (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  stockCode VARCHAR(10) NOT NULL,
  stockName VARCHAR(50) NOT NULL,
  currency VARCHAR(5) NOT NULL,
  openPrice double,
  curPrice double,
  changeValue double,
  changePercentage double,
  high double,
  low double
);

INSERT INTO JkeStockQuote (stockCode, stockName, currency, openPrice, curPrice, changeValue, changePercentage, high, low) VALUES
  ('ABDP', 'AB DYNAMICS', 'GBX', 2600, 2685, 85, 3.27, 2700, 2575),
  ('ASLI', 'ABSTD EURO LOG.', 'GBX', 92, 92.60, 0.60, 0.65, 93, 92),
  ('BGO', 'BANGO', 'GBX', 128.50, 130.50, -2, -1.51, 132.50, 130.50),
  ('BARC', 'BARCLAYS', 'GBX', 136.16, 137.54, 1.38, 1.01, 137.82, 135.18),
  ('BPM', 'B.P MARSH', 'GBX', 224.00, 220.00, -4, 1.33, 224.00, 221.00),
  ('C4XD', 'C4X DISCOVERY HOLDINGS', 'GBX', 39.00, 40.00, 1, 2.56, 40.50, 38),
  ('D4T4', 'D4T4 SOLUTIONS', 'GBX', 240.00, 240.00, 0, 3.27, 242.50, 240.00),
  ('ETX', 'E-THERAPEUTICS', 'GBX', 3.40, 3.45, 0.05, 1.47, 3.45, 3.40),
  ('FCIT', 'F &C IV.TST', 'GBX', 711.00, 711.00, 0, 0, 712, 711),
  ('GMP', 'GABELLI MERGER', 'USD', 8.80, 9.05, 0.25, 2.8, 9.40, 8.70),
  ('HVPD', 'HARBOURVEST $', 'USD', 20.60, 20.70, 0.10, 0.48, 20.80, 20.45),
  ('KBT', 'K3 BUS.TECH.', 'GBX', 216, 213, 3, 1.38, 220, 213),
  ('K3C', 'K3 CAPITAL GRO.', 'GBX', 150, 149, -1, 0.66, 153, 148),
  ('KAKU', 'KAKUZI', 'GBX', 92.50, 92.50, 0, 0, 92.50, 92.50),
  ('KZG', 'KAZERA GLOBAL', 'GBX', 0.62, 0.6, -0.02, 3.22, 0.65, 0.5),
  ('N4P', 'N4 PHARMA PLC', 'GBX', 3.42, 3.46, 0.04, 1.16, 3.60, 3.42),
  ('NANO', 'NANOCO', 'GBX', 12.85, 12.75, -0.1, 0.77, 12.85, 12.55),
  ('OCDO', 'OCADO', 'GBX', 1321.00, 1322.00, 1, 0.07, 1337.00, 1314.00),
  ('OAP3', 'OCTOPUS AP', 'GBX', 43.30, 43.00, -0.30, 0.69, 43.80, 42.80),
  ('ODX', 'OMEGA DIA', 'GBX', 10.30, 1.40, 0.1, 0.9, 10.50, 10.25);

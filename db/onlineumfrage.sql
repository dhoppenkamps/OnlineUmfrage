-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 16. Apr 2014 um 02:18
-- Server Version: 5.6.16
-- PHP-Version: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `onlineumfrage`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `antworten`
--

CREATE TABLE IF NOT EXISTS `antworten` (
  `ID_Antwort` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ID_Umfrage` bigint(20) NOT NULL,
  `Antwort1` varchar(255) NOT NULL,
  `Antwort2` varchar(255) NOT NULL,
  `Antwort3` varchar(255) NOT NULL,
  `Antwort4` varchar(255) NOT NULL,
  `Antwort5` varchar(255) NOT NULL,
  PRIMARY KEY (`ID_Antwort`),
  UNIQUE KEY `ID_Antwort` (`ID_Antwort`),
  KEY `ID_Umfrage` (`ID_Umfrage`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `kunde`
--

CREATE TABLE IF NOT EXISTS `kunde` (
  `ID_Kunde` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `Vorname` varchar(100) NOT NULL,
  `Nachname` varchar(100) NOT NULL,
  `Email` varchar(255) NOT NULL,
  PRIMARY KEY (`ID_Kunde`),
  UNIQUE KEY `ID_Kunde` (`ID_Kunde`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `token`
--

CREATE TABLE IF NOT EXISTS `token` (
  `Token` varchar(255) NOT NULL,
  `ID_Umfrage` bigint(20) NOT NULL,
  `ID_Kunde` bigint(20) NOT NULL,
  PRIMARY KEY (`Token`),
  KEY `ID_Umfrage` (`ID_Umfrage`),
  KEY `ID_Kunde` (`ID_Kunde`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `umfrage`
--

CREATE TABLE IF NOT EXISTS `umfrage` (
  `ID_Umfrage` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `Titel` varchar(255) NOT NULL,
  `Frage1` int(255) NOT NULL,
  `Frage2` int(255) NOT NULL,
  `Frage3` int(255) NOT NULL,
  `Frage4` int(255) NOT NULL,
  `Frage5` int(255) NOT NULL,
  `Beginn` date NOT NULL,
  `Ende` date NOT NULL,
  `Stimmen` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_Umfrage`),
  UNIQUE KEY `ID_Umfrage` (`ID_Umfrage`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `umfrage_x_token`
--

CREATE TABLE IF NOT EXISTS `umfrage_x_token` (
  `ID_Teilnahme` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `Token` varchar(255) NOT NULL,
  `ID_Umfrage` bigint(20) NOT NULL,
  PRIMARY KEY (`ID_Teilnahme`),
  UNIQUE KEY `ID_Teilnahme` (`ID_Teilnahme`),
  KEY `Token` (`Token`,`ID_Umfrage`),
  KEY `ID_Umfrage` (`ID_Umfrage`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

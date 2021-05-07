import React, { Component } from 'react';
import ListFighterComponent from "./ListFighterComponent";
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import FighterComponent from "./FighterComponent";
import HomeComponent from "./HomeComponent";
import FighterAppComponent from "./FighterAppComponent";
import ListCovidTestComponent from "./ListCovidTestComponent";
import CovidTestComponent from "./CovidTestComponent";
import ListTournamentComponent from "./ListTournamentComponent";
import TournamentComponent from "./TournamentComponent";
import AdminComponent from "./AdminComponent";
import ScheduleComponent from "./ScheduleComponent";

class TournamentApp extends Component {
    render() {
        return (
            <Router>
                <>
                    <h1>Tournament Application</h1>
                    <Switch>
                        <Route path="/" exact component={HomeComponent} />
                        <Route path="/administrator" exact component={AdminComponent} />
                        <Route path="/administrator/fighters" exact component={ListFighterComponent} />
                        <Route path="/administrator/fighters/:idFighter" component={FighterComponent} />
                        <Route path="/fighter/register" component={FighterAppComponent} />
                        <Route path="/administrator/covidtests" exact component={ListCovidTestComponent} />
                        <Route path="/administrator/covidtests/:idCovidTest" component={CovidTestComponent} />
                        <Route path="/administrator/tournaments" exact component={ListTournamentComponent} />
                        <Route path="/administrator/tournaments/:idTournament" component={TournamentComponent} />
                        <Route path="/administrator/schedule" component={ScheduleComponent} />
                    </Switch>
                </>
            </Router>
        )
    }
}

export default TournamentApp
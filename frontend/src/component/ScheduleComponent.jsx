import React, {Component} from 'react';
import {ErrorMessage, Field, Form, Formik} from "formik";
import MatchTestDataService from "../service/MatchTestDataService";
import TournamentDataService from "../service/TournamentDataService";
import FighterDataService from "../service/FighterDataService";

class ScheduleComponent extends Component{

    constructor(props) {
        super(props);
        this.state = {
            dateTimeStart: '',
            tournamentTitle: '',
            location: '',
            matches: [],
            tournamentType: '',
            fighters: [],
            firstSubmit: 0,
            currentDate: null,
            tournament: null,
            message: null,
            errorMessage: null,
            messageDng: null
        }
        this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this)
        this.refreshMatches = this.refreshMatches.bind(this)
        this.deleteMatchClicked = this.deleteMatchClicked.bind(this)
        this.retrieveFighters = this.retrieveFighters.bind(this)
        this.firstSubmit = this.firstSubmit.bind(this)
    }

    componentDidMount() {
        this.retrieveFighters()
    }

    retrieveFighters(){
        FighterDataService.retrieveAllFightersDTO()
            .then(
                response => {
                    this.setState({fighters: response.data})
                }
            )
    }

    validate(values) {
        let errors = {}
        if (!values.location) {
            errors.location = 'Enter a location for the event!'
        }
        if (values.tournamentTitle === '') {
            errors.dateTimeStart = 'Enter a date for the event!'
        }
        if (!values.dateTimeStart) {
            errors.dateTimeStart = 'Enter a date for the event!'
        }
        if(!values.tournamentType){
            errors.tournamentType = 'Select a tournament type for the event'
        }
        return errors
    }

    deleteMatchClicked(idMatch) {
        MatchTestDataService.deleteMatch(parseInt(idMatch))
            .then(
                response => {
                    this.setState({errorMessage: null})
                    this.refreshMatches()
                }
            )
    }

    firstSubmit(values){
        MatchTestDataService.deleteAllMatches()
            .then(response => {console.log(response.data)})
            .catch(error => {this.setState({errorMessage: error.response.data})})
        this.setState({currentDate: values.dateTimeStart})
        let tournament = {
            idTournament: -1,
            title: values.tournamentTitle,
            location: values.location,
            dateTimeStart: values.dateTimeStart,
            type: values.tournamentType
        }
        TournamentDataService.createTournament2(tournament)
            .then(
                response => {
                    this.setState({tournament: response.data})
                    FighterDataService.testFighters(values.dateTimeStart)
                        .then(
                            () => {
                                this.setState({message: "Fighters have been tested!"})
                                this.retrieveFighters()
                            }
                        )
                    MatchTestDataService.createMatch(values.dateTimeStart, response.data)
                        .then(this.refreshMatches)
                        .catch(
                            error => {
                                this.setState({errorMessage: error.response.data})
                                this.setState({message: null})
                            }
                        )
                }
            )
        this.setState({firstSubmit: this.state.firstSubmit+1})
    }

    onSubmit(values) {
        // eslint-disable-next-line
        if(this.state.firstSubmit == 0){
            this.firstSubmit(values)
        }
        else{
            if(values.tournamentTitle !== this.state.tournament.title || values.tournamentType !== this.state.tournament.type ||
               values.location !== this.state.tournament.location || values.dateTimeStart !== this.state.tournament.dateTimeStart) {
                this.setState({messageDng: "The input fields have been modified! A new tournament will be created with the new inputs!"})
                this.setState({firstSubmit: 0})
                this.firstSubmit(values)
            }
            else{
                MatchTestDataService.computeNextPeriod(this.state.currentDate, values.tournamentType)
                    .then(
                        response => {
                            this.setState({currentDate: response.data})
                            MatchTestDataService.createMatch(response.data, this.state.tournament)
                                .then(() => {
                                    this.refreshMatches()
                                    this.retrieveFighters()
                                    this.setState({firstSubmit: this.state.firstSubmit+1})
                                })
                                .catch(
                                    error => {
                                        this.setState({errorMessage: error.response.data})
                                        this.setState({message: null})
                                    }
                                )
                        }
                    )
            }
        }
        // eslint-disable-next-line
        if(this.state.firstSubmit == 2){
            this.setState({messageDng: null})
        }
    }

    refreshMatches(){
        MatchTestDataService.retrieveAllMatches()
            .then(
                response => {
                    this.setState({ matches: response.data})
                }
            )
    }

    render() {
        const {tournamentTitle,dateTimeStart, location, tournamentType} = this.state
        return(
            <div className='container'>
                {this.state.messageDng && <div className="alert alert-warning">{this.state.messageDng}</div>}
                {this.state.message && <div className="alert alert-success">{this.state.message}</div>}
                {this.state.errorMessage && <div className="alert alert-danger">{this.state.errorMessage}</div>}
                <div className="row">
                    <div className="col">
                        <Formik
                            initialValues={{ dateTimeStart,tournamentTitle, location, tournamentType}}
                            onSubmit={this.onSubmit}
                            validateOnChange={false}
                            validateOnBlur={false}
                            validate={this.validate}
                            enableReinitialize={true}
                        >
                            {
                                (props) => (
                                    <Form>
                                        <ErrorMessage name="tournamentTitle" component="div"
                                                      className="alert alert-warning" />
                                        <ErrorMessage name="location" component="div"
                                                      className="alert alert-warning" />
                                        <ErrorMessage name="dateTimeStart" component="div"
                                                      className="alert alert-warning" />
                                        <ErrorMessage name="tournamentType" component="div"
                                                      className="alert alert-warning" />
                                        <fieldset className="form-group">
                                            <label>Enter the title of the tournament</label>
                                            <Field className="form-control" type="text" name="tournamentTitle"/>
                                        </fieldset>
                                        <fieldset className="form-group">
                                            <label>Enter the location of the tournament</label>
                                            <Field className="form-control" type="text" name="location"/>
                                        </fieldset>
                                        <fieldset className="form-group">
                                            <label>Enter the beginning date of the tournament</label>
                                            <Field className="form-control" type="date" name="dateTimeStart"/>
                                        </fieldset>
                                        <fieldset className="form-group">
                                            <label>Select the occurrence rate of the matches</label>
                                            <div role="group" aria-labelledby="my-radio-group">
                                                <label>
                                                    <Field type="radio" name="tournamentType" value="Weekly" />
                                                    Weekly
                                                </label>
                                                <label>
                                                    <Field type="radio" name="tournamentType" value="Monthly" />
                                                    Monthly
                                                </label>
                                            </div>
                                        </fieldset>
                                        <button className="btn btn-success" type="submit" disabled={this.state.errorMessage}>Schedule</button>
                                    </Form>
                                )
                            }
                        </Formik>
                    </div>
                    <div className="col">
                        <h3>Fighters</h3>
                        <div className="table table-fighter">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Weight</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                this.state.fighters.map(
                                    fighter =>
                                        <tr bgcolor={fighter.color} key={fighter.name}>
                                            <td>{fighter.name}</td>
                                            <td>{fighter.weight}</td>
                                        </tr>
                                )
                            }
                            </tbody>
                        </div>
                    </div>
                </div><br/>
                <h5>Current date:{this.state.currentDate}</h5>
                <br/>
                <div className="table">
                    <thead>
                        <tr>
                            <th>Id Match</th>
                            <th>Tournament Title</th>
                            <th>Fighter 1</th>
                            <th>Fighter 2</th>
                            <th>Winner</th>
                            <th>Rounds</th>
                            <th>Date</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                    {
                        this.state.matches.map(
                            match =>
                                <tr key={match.idMatch}>
                                    <td>{match.idMatch}</td>
                                    <td>{match.tournamentTitle}</td>
                                    <td>{match.nameFighter1}</td>
                                    <td>{match.nameFighter2}</td>
                                    <td>{match.winner}</td>
                                    <td>{match.rounds}</td>
                                    <td>{match.dateTimeStart}</td>
                                    <td><button className="btn btn-warning" onClick={() => this.deleteMatchClicked(match.idMatch)}>Delete</button></td>
                                </tr>
                        )
                    }
                    </tbody>
                </div>
                <button className="btn btn-success" onClick={this.refreshMatches}>Refresh matches</button>
            </div>
        )
    }
}

export default ScheduleComponent
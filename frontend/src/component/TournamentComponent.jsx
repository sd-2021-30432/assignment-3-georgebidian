import React, { Component } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import TournamentDataService from "../service/TournamentDataService";

class TournamentComponent extends Component{

    constructor(props) {
        super(props)

        this.state = {
            idTournament: this.props.match.params.idTournament,
            title: '',
            location: '',
            dateTimeStart: null,
            type: ''
        }
        this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this)
    }

    onSubmit(values) {

        let tournament = {
            idTournament: parseInt(this.state.idTournament),
            title: values.title,
            location: values.location,
            dateTimeStart: values.dateTimeStart,
            type: values.type
        }
        console.log(this.state.idTournament)
        // eslint-disable-next-line
        if(this.state.idTournament == -1){
            TournamentDataService.createTournament(tournament)
                .then(() => this.props.history.push('/administrator/tournaments'))
        }else{
            TournamentDataService.updateTournament(tournament.idTournament, tournament)
                .then(() => this.props.history.push('/administrator/tournaments'))
        }
    }

    componentDidMount() {
        if (this.state.idTournament === -1) {
            return;
        }
        TournamentDataService.retrieveTournament(this.state.idTournament)
            .then(
                response =>
                    this.setState({
                        title: response.data.title,
                        location: response.data.location,
                        dateTimeStart: response.data.dateTimeStart,
                        type: response.data.type
                    }));
    }

    validate(values) {
        let errors = {}
        if (!values.title) {
            errors.title = 'Enter a title'
        }
        if (!values.location) {
            errors.location = 'Enter a location'
        }
        if(!values.dateTimeStart){
            errors.dateTimeStart = 'Enter a Date Time Start value'
        }
        return errors
    }

    render() {
        let { idTournament, title, location, dateTimeStart, type } = this.state
        return (
            <div>
                <h3>Tournament</h3>
                <div className="container">
                    <Formik
                        initialValues={{ idTournament, title, location, dateTimeStart, type }}
                        onSubmit={this.onSubmit}
                        validateOnChange={false}
                        validateOnBlur={false}
                        validate={this.validate}
                        enableReinitialize={true}
                    >
                        {
                            (props) => (
                                <Form>
                                    <ErrorMessage name="title" component="div"
                                                  className="alert alert-warning" />
                                    <ErrorMessage name="location" component="div"
                                                  className="alert alert-warning" />
                                    <ErrorMessage name="dateTimeStart" component="div"
                                                  className="alert alert-warning" />
                                    <fieldset className="form-group">
                                        <label>Id</label>
                                        <Field className="form-control" type="text" name="idTournament" disabled/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Title</label>
                                        <Field className="form-control" type="text" name="title"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Location</label>
                                        <Field className="form-control" type="text" name="location"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Date Time Start</label>
                                        <Field className="form-control" type="date" name="dateTimeStart"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Type</label>
                                        <div role="group" aria-labelledby="my-radio-group">
                                            <label>
                                                <Field type="radio" name="type" value="Weekly" />
                                                Weekly
                                            </label>
                                            <label>
                                                <Field type="radio" name="type" value="Monthly" />
                                                Monthly
                                            </label>
                                        </div>
                                    </fieldset>
                                    <button className="btn btn-success" type="submit">Save</button>
                                </Form>
                            )
                        }
                    </Formik>

                </div>
            </div>
        )
    }
}

export default TournamentComponent
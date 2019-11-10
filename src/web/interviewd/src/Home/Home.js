import React, { Component } from 'react';

class Home extends Component {
  login() {
    this.props.auth.login();
  }

  componentDidMount() {
    const headers = new Headers();
    var accessToken = this.props.auth.getAccessToken();
    var idToken = this.props.auth.getIdToken();
    console.log(accessToken);
    console.log(idToken);
    headers.append('Authorization', eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik1rUXlSREV6UlRWQk1UazVRME0zUkRkRU1VTkZPVGs0UlRnNE5UazFRMFV6T1VSQ05FRTVNQSJ9.eyJpc3MiOiJodHRwczovL2ludGVydmlld2QuYXV0aDAuY29tLyIsInN1YiI6ImF1dGgwfDVjZTljMmMwZjkyNTg5MTExODhiN2M0MiIsImF1ZCI6Ik5ESDAxTHNnVEdvOUFQVUh4bDE3UHBvZHlhMVBzbm9LIiwiaWF0IjoxNTU4ODgwNDA2LCJleHAiOjE1NTg5MTY0MDYsImF0X2hhc2giOiJzcVhUUEU0TU5uQkJZUHlpaVJmVXJ3Iiwibm9uY2UiOiI4bXd3SUhoOG9pU09TdVJwNHlQemNQdVo0cUxtQlRvZiJ9.DNsHIW9prTbGbpzNCdmYV_mZ0GFYynjVt1XcfMrMzmJ_bPju_yE47VhXTPjqvv1FsyXRs63ykFGV1qkgyw4mRmKsdEy4CphjkL9pWnxRJAJeGkdIvKbs9Ddf8Su-CYPUcs-HeRUMGVW5h4x_-S1u818E4yLQOCLbA5W2xEqIcmv01L21VFDJ45Vb41Mh6CTjW8gpgALRTxmdHa-47_ywV0nNUyq6nxb7iCvrc8L-C4rdJdGD_DyKyg4KHmRNggaXFcJ6f-nOOSYtqi_rRyYT83Xy6ymz97hQafN7x1MfUUnCNgqiYRw1FVS-TPv9_kJy74CrNC5xdFONBihEM9DC6A);
    fetch("/questions", {
      headers: headers
    })
      .then(res => res.json())
      .then(
        (result) => {
          console.log(result);
          // this.setState({
          //   isLoaded: true,
          //   items: result.items
          // });
        },
        // Note: it's important to handle errors here
        // instead of a catch() block so that we don't swallow
        // exceptions from actual bugs in components.
        (error) => {
          console.error(error);
          // this.setState({
          //   isLoaded: true,
          //   error
          // });
        }
      )
  }

  render() {
    const { isAuthenticated } = this.props.auth;
    return (
      <div className="container">
        {
          isAuthenticated() && (
              <h4>
                You are logged in!
              </h4>
            )
        }
        {
          !isAuthenticated() && (
              <h4>
                You are not logged in! Please{' '}
                <a style={{ cursor: 'pointer' }}
                  onClick={this.login.bind(this)}>
                  Log In
                </a>
                {' '}to continue.
              </h4>
            )
        }
      </div>
    );
  }
}

export default Home;

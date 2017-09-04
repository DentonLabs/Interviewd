using System.Threading.Tasks;
using Interviewd.Application.Dto;
using NUnit.Framework;
using Ploeh.AutoFixture;

namespace Interviewd.Tests.Api
{
    [TestFixture]
    public class WhenTestingCandidates : WhenTesting
    {
        [Test]
        public async Task ShouldBeAbleToCreateACandidate()
        {
            var requestCandidate = Fixture.Build<CandidateDto>()
                .Without(o => o.Id)
                .Create();

            var httpResponseMessage = await HttpClient.PostAsync(
                ApiRoutes.CandidatesRoute,
                requestCandidate.ToStringContent());

            var responseCandidate = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetLikenessContent<CandidateDto>();

            responseCandidate.ShouldEqual(requestCandidate);
        }
    }
}
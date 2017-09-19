using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Interviewd.Application.Dto;
using Interviewd.Domain.Model;
using Jmansar.SemanticComparisonExtensions;
using NUnit.Framework;

namespace Interviewd.Tests.Api
{
    [TestFixture]
    public class WhenTestingCandidates : WhenTesting
    {
        [Test]
        public async Task ShouldBeAbleToCreateACandidate()
        {
            var requestCandidate = Stubber.StubCandidateDto();

            var httpResponseMessage = await ApiClient.PostCandidate(requestCandidate);

            var responseCandidate = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetLikenessContent<CandidateDto>();

            responseCandidate.ShouldEqual(requestCandidate);
        }

        [Test]
        public async Task ShouldBeAbleToGetAllCandidates()
        {
            var dbCandidates = await Arranger.CreateCandidates();

            var httpResponseMessage = await ApiClient.GetAllCandidates();

            var responseCandidateDtos = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetContent<IEnumerable<CandidateDto>>();

            var responseCandidates = Mapper.Map<IEnumerable<Candidate>>(responseCandidateDtos);

            responseCandidates = responseCandidates.Where(rc => dbCandidates.Any(dc => dc.Id == rc.Id));

            Assert.IsTrue(responseCandidates.CompareCollectionsUsingLikeness(dbCandidates));
        }

        [Test]
        public async Task ShouldBeAbleToGetCandidate()
        {
            var dbCandidate = await Arranger.CreateCandidate();

            var responseCandidateDto = await ApiClient.GetCandidate(dbCandidate.Id)
                .AwaitGetSuccessfulResponse<CandidateDto>();

            responseCandidateDto.ToLikeness()
                .ShouldEqual(Mapper.Map<CandidateDto>(dbCandidate));
        }
    }
}